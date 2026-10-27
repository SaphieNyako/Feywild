package com.saphienyako.feywild.item;

import com.saphienyako.feywild.compat.ModCompat;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.item.base.ToolTipBaseItem;
import com.saphienyako.quest_giver.quest.data.QuestData;
import com.saphienyako.quest_giver.quest.task.SpecialTask;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class FeyDustItem extends ToolTipBaseItem {

    public FeyDustItem(Properties properties) {
        super(properties, Component.translatable("message.feywild.fey_dust"));
    }

    @SuppressWarnings("resource")
    @Nonnull
    @Override
    public InteractionResult interactLivingEntity(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull LivingEntity target, @Nonnull InteractionHand hand) {
        if (!player.level().isClientSide()) {
            if (target instanceof Sheep && ModCompat.QUEST_GIVER_LOADED) {
                target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 80, 3));
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, "fey_dust_sheep");
            } else
                target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, FeywildConfig.feyDustDuration, 2));

        } else {
            return InteractionResult.FAIL;
        }
        CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.isCreative()) stack.shrink(1);

        return InteractionResult.SUCCESS;
    }
}
