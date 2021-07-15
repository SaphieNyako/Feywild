package com.feywild.feywild.item;

import com.feywild.feywild.util.configs.Config;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class FeyDust extends TooltipItem {

    public FeyDust(Properties properties) {
        super(properties);
    }

    @Override
    public List<ITextComponent> getTooltip(ItemStack stack, World world) {
        return ImmutableList.of(new TranslationTextComponent("message.feywild.fey_dust"));
    }

    //Test
    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(@Nonnull ItemStack stack, PlayerEntity playerIn, @Nonnull LivingEntity target, @Nonnull Hand hand) {
        if (playerIn.level.isClientSide()) return ActionResultType.SUCCESS;

        if (target instanceof SheepEntity) {
            target.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2));

        } else {
            target.addEffect(new EffectInstance(Effects.LEVITATION, Config.FEY_DUST_DURATION.get(), 2));
        }

        stack.shrink(1);

        return ActionResultType.SUCCESS;
    }
}
