package com.saphienyako.feywild.item;

import com.saphienyako.feywild.config.ModConfig;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.*;
import net.minecraft.world.World;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


public class FeyDustItem extends Item {

    private Food food;

    public FeyDustItem(Properties properties) {
        super(properties);
        this.updateFood();
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World level,@Nonnull List<ITextComponent> tooltip,@Nonnull ITooltipFlag flag) {
        if (level != null) {
            TranslationTextComponent text = new TranslationTextComponent("message.feywild.fey_dust");
            text.getStyle().withColor(Color.fromLegacyFormat(TextFormatting.BLUE));
            tooltip.add(text);
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Nonnull
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {
        World level = player.level;
        if (!level.isClientSide) {
            target.addEffect(new EffectInstance(Effects.LEVITATION, ModConfig.COMMON.fey_dust_duration.get(), 2));
        } else {
            return ActionResultType.FAIL;
        }

        CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) player, stack);
        player.awardStat(Stats.ITEM_USED.get(this));

        if (!player.isCreative()) stack.shrink(1);

        return  ActionResultType.sidedSuccess(player.level.isClientSide);
    }

    @Nullable
    @Override
    public Food getFoodProperties() {
        // Overridden instead of item properties, so it will
        // instantly change on config reload
        return this.food;
    }

    public void updateFood() {
        this.food = new Food.Builder().effect(() -> new EffectInstance(Effects.LEVITATION, ModConfig.COMMON.fey_dust_duration.get(), 2), 1).build();
    }
}
