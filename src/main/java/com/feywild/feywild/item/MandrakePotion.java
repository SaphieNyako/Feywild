package com.feywild.feywild.item;

import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.base.ItemBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MandrakePotion extends ItemBase {

    public MandrakePotion(ModX mod, Item.Properties properties) {
        super(mod, properties);
    }
    
    @Nonnull
    @Override
    public UseAnim getUseAnimation(@Nonnull ItemStack stack) {
        return UseAnim.DRINK;
    }
    
    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
    
    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity living) {
        Player player = living instanceof Player ? (Player) living : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
            QuestData.get((ServerPlayer) player).reset();
            if (!player.isCreative()) {
                stack.shrink(1);
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return stack;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslatableComponent("message.feywild.mandrake_potion"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
