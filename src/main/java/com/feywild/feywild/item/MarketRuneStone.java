package com.feywild.feywild.item;

import com.feywild.feywild.world.dimension.ModDimensions;
import com.feywild.feywild.world.dimension.market.MarketHandler;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MarketRuneStone extends TooltipItem {

    public MarketRuneStone(ModX mod, Properties prop) {
        super(mod, prop, new TranslationTextComponent("message.feywild.market_scroll"));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        if (world.isClientSide) {
            if (world.dimension() == World.OVERWORLD || world.dimension() == ModDimensions.MARKET_PLACE_DIMENSION) {
                return ActionResult.sidedSuccess(player.getItemInHand(hand), true);
            } else {
                return ActionResult.fail(player.getItemInHand(hand));
            }
        } else if (player instanceof ServerPlayerEntity) {
            if (world.dimension() == ModDimensions.MARKET_PLACE_DIMENSION) {
                if (MarketHandler.teleportToOverworld((ServerPlayerEntity) player)) {
                    player.getCooldowns().addCooldown(this, 60);
                    return ActionResult.sidedSuccess(player.getItemInHand(hand), false);
                } else {
                    return ActionResult.fail(player.getItemInHand(hand));
                }
            } else {
                if (MarketHandler.teleportToMarket((ServerPlayerEntity) player)) {
                    player.getCooldowns().addCooldown(this, 60);
                    return ActionResult.sidedSuccess(player.getItemInHand(hand), false);
                } else {
                    return ActionResult.fail(player.getItemInHand(hand));
                }
            }
        } else {
            return ActionResult.pass(player.getItemInHand(hand));
        }
    }
}
