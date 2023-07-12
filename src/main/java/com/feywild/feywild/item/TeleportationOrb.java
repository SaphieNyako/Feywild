package com.feywild.feywild.item;

import com.feywild.feywild.world.FeywildDimensions;
import com.feywild.feywild.world.feywild.FeywildTeleporter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;

public class TeleportationOrb extends TooltipItem {

    public TeleportationOrb(ModX mod, Properties prop) {
        super(mod, prop, Component.translatable("message.feywild.teleportation_orb"));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (level.isClientSide) {
            if (level.dimension() == Level.OVERWORLD || level.dimension() == FeywildDimensions.FEYWILD) {
                return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), true);
            } else {
                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }
        } else if (player instanceof ServerPlayer serverPlayer) {
            if (level.dimension() == FeywildDimensions.FEYWILD) {
                if (FeywildTeleporter.teleportToOverworld(serverPlayer)) {
                    player.getCooldowns().addCooldown(this, 60);
                    return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), false);
                } else {
                    return InteractionResultHolder.fail(player.getItemInHand(hand));
                }
            } else {
                if (FeywildTeleporter.teleportToFeywild(serverPlayer)) {
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
