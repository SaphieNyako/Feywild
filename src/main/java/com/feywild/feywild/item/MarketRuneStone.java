package com.feywild.feywild.item;

import com.feywild.feywild.world.FeywildDimensions;
import com.feywild.feywild.world.market.MarketHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item.Properties;

public class MarketRuneStone extends TooltipItem {

    public MarketRuneStone(ModX mod, Properties prop) {
        super(mod, prop, Component.translatable("message.feywild.market_rune_stone"));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (level.isClientSide) {
            if (level.dimension() == Level.OVERWORLD || level.dimension() == FeywildDimensions.MARKETPLACE) {
                return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), true);
            } else {
                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }
        } else if (player instanceof ServerPlayer serverPlayer) {
            if (level.dimension() == FeywildDimensions.MARKETPLACE) {
                if (MarketHandler.teleportToOverworld(serverPlayer)) {
                    player.getCooldowns().addCooldown(this, 60);
                    if (!player.isCreative()) {
                        player.getItemInHand(hand).shrink(1);
                        player.addItem(new ItemStack(ModItems.inactiveMarketRuneStone));
                    }
                    return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), false);
                } else {
                    return InteractionResultHolder.fail(player.getItemInHand(hand));
                }
            } else {
                if (MarketHandler.teleportToMarket(serverPlayer)) {
                    player.getCooldowns().addCooldown(this, 60);
                    return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), false);
                } else {
                    return InteractionResultHolder.fail(player.getItemInHand(hand));
                }
            }
        } else {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
    }
}
