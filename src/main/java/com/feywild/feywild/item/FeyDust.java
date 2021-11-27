package com.feywild.feywild.item;

import com.feywild.feywild.block.MagicalBrazierBlock;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.SpecialTask;
import com.feywild.feywild.quest.util.SpecialTaskAction;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.base.ItemBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class FeyDust extends ItemBase {

    private FoodProperties food;

    public FeyDust(ModX mod, Properties properties) {
        super(mod, properties);
        this.updateFood();
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslatableComponent("message.feywild.fey_dust"));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Nonnull
    @Override
    public InteractionResult interactLivingEntity(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull LivingEntity target, @Nonnull InteractionHand hand) {
        if (!player.level.isClientSide) {
            if (target instanceof Sheep) {
                target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, Math.max(60, MiscConfig.fey_dust_ticks), 2));
                if (player instanceof ServerPlayer) {
                    QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.LEVITATE_SHEEP);
                }
            } else {
                target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, MiscConfig.fey_dust_ticks, 2));
            }
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.isCreative()) stack.shrink(1);
        }
        return InteractionResult.sidedSuccess(player.level.isClientSide);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof MagicalBrazierBlock) {
            if (!context.getLevel().getBlockState(context.getClickedPos()).getValue(MagicalBrazierBlock.BRAZIER_LIT)) {
                if (!context.getLevel().isClientSide) {
                    if (!Objects.requireNonNull(context.getPlayer()).isCreative()) {
                        context.getPlayer().getItemInHand(context.getHand()).shrink(1);
                    }
                    context.getLevel().setBlock(context.getClickedPos(), context.getLevel().getBlockState(context.getClickedPos()).setValue(MagicalBrazierBlock.BRAZIER_LIT, true), 2);
                }
                return InteractionResult.sidedSuccess(Objects.requireNonNull(context.getPlayer()).level.isClientSide);
            }
        }
        return super.useOn(context);
    }

    @Nullable
    @Override
    public FoodProperties getFoodProperties() {
        // Overridden instead of item properties, so it will
        // instantly change on config reload
        return this.food;
    }

    public void updateFood() {
        this.food = new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.LEVITATION, MiscConfig.fey_dust_ticks, 1), 1).build();
    }
}
