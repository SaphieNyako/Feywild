package com.feywild.feywild.item;

import com.feywild.feywild.world.dimension.market.MarketPlaceDimension;
import com.feywild.feywild.world.dimension.market.setup.MarketHandler;
import org.moddingx.libx.mod.ModX;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item.Properties;

public class MarketRuneStone extends TooltipItem {

    public MarketRuneStone(ModX mod, Properties prop) {
        super(mod, prop, new TranslatableComponent("message.feywild.market_scroll"));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (level.isClientSide) {
            if (level.dimension() == Level.OVERWORLD || level.dimension() == MarketPlaceDimension.MARKET_PLACE_DIMENSION) {
                return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), true);
            } else {
                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }
        } else if (player instanceof ServerPlayer) {
            if (level.dimension() == MarketPlaceDimension.MARKET_PLACE_DIMENSION) {
                if (MarketHandler.teleportToOverworld((ServerPlayer) player)) {
                    player.getCooldowns().addCooldown(this, 60);
                    return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), false);
                } else {
                    return InteractionResultHolder.fail(player.getItemInHand(hand));
                }
            } else {
                if (MarketHandler.teleportToMarket((ServerPlayer) player)) {
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
