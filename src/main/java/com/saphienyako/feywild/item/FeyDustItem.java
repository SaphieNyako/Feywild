package com.saphienyako.feywild.item;

import com.saphienyako.feywild.config.FeywildConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FeyDustItem extends Item {

    private FoodProperties food;

    public FeyDustItem(Properties properties) {
        super(properties);
        this.updateFood();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("message.feywild.fey_dust").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(stack, context, tooltip, flag);
    }


    // TODO add LIVITATION_IMMUUM tag or config
    //TODO add quest
    @Nonnull
    @Override
    public InteractionResult interactLivingEntity(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull LivingEntity target, @Nonnull InteractionHand hand) {
        if (!player.level().isClientSide) {
           /* if (target instanceof Sheep) {
                target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, Math.max(60, 30), 2));

                if (player instanceof ServerPlayer) {
                    QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.LEVITATE_SHEEP);
                }
            } else if (!target.getType().is(ModEntityTags.LEVITATION_IMMUNE)) */
                target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, FeywildConfig.feyDustDuration, 2)); //TODO add config
        } else {
            return InteractionResult.FAIL;
        }
        CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.isCreative()) stack.shrink(1);

        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }

    @Nullable
    @Override
    public FoodProperties getFoodProperties(ItemStack stack, LivingEntity entity) {
        // Overridden instead of item properties, so it will
        // instantly change on config reload
        return this.food;
    }

    public void updateFood() {
        this.food = new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.LEVITATION, FeywildConfig.feyDustDuration, 2), 1).build();
    }
}
