package com.feywild.feywild.item;

import com.feywild.feywild.util.TooltipHelper;
import com.feywild.feywild.util.configs.Config;
import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FeyDust extends ItemBase {

    public FeyDust(ModX mod, Properties properties) {
        super(mod, properties);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslationTextComponent("message.feywild.fey_dust"));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull LivingEntity target, @Nonnull Hand hand) {
        if (!player.level.isClientSide) {
            if (target instanceof SheepEntity) {
                target.addEffect(new EffectInstance(Effects.LEVITATION, 60, 2));
            } else {
                target.addEffect(new EffectInstance(Effects.LEVITATION, Config.FEY_DUST_DURATION.get(), 2));
            }
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.isCreative()) stack.shrink(1);
        }
        return ActionResultType.sidedSuccess(player.level.isClientSide);
    }
}
