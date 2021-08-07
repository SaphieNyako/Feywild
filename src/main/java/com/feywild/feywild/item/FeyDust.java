package com.feywild.feywild.item;

import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.SpecialTask;
import com.feywild.feywild.quest.util.SpecialTaskAction;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Food;
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

    private Food food;
    
    public FeyDust(ModX mod, Properties properties) {
        super(mod, properties);
        this.updateFood();
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
                target.addEffect(new EffectInstance(Effects.LEVITATION, Math.max(60, MiscConfig.fey_dust_ticks), 2));
                if (player instanceof ServerPlayerEntity) {
                    QuestData.get((ServerPlayerEntity) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.LEVITATE_SHEEP);
                }
            } else {
                target.addEffect(new EffectInstance(Effects.LEVITATION, MiscConfig.fey_dust_ticks, 2));
            }
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.isCreative()) stack.shrink(1);
        }
        return ActionResultType.sidedSuccess(player.level.isClientSide);
    }

    @Nullable
    @Override
    public Food getFoodProperties() {
        // Overridden instead of item properties, so it will
        // instantly change on config reload
        return this.food;
    }
    
    public void updateFood() {
        this.food = new Food.Builder().effect(() -> new EffectInstance(Effects.LEVITATION, MiscConfig.fey_dust_ticks, 1), 1).build();
    }
}
