package com.feywild.feywild.world.market;

import com.feywild.feywild.FeyPlayerData;
import com.feywild.feywild.world.FeywildDimensions;
import com.feywild.feywild.world.teleport.SimpleTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

public class MarketHandler {

    public static void update(MinecraftServer server) {
        ServerLevel market = server.getLevel(FeywildDimensions.MARKETPLACE);
        if (market != null) {
            MarketData data = MarketData.get(market);
            if (data != null) {
                data.update(server, () -> market.getPlayers(player -> true).forEach(player -> {
                    teleportToOverworld(player);
                    player.displayClientMessage(Component.translatable("message.feywild.market_closed"), false);
                }));
            }
        }
    }

    public static boolean teleportToMarket(ServerPlayer player) {
        if (player.level().dimension() == Level.OVERWORLD) {
            ServerLevel targetLevel = player.level().getServer().getLevel(FeywildDimensions.MARKETPLACE);
            if (targetLevel != null) {
                MarketData data = MarketData.get(targetLevel);
                if (data != null) {
                    if (data.isOpen()) {
                        FeyPlayerData.get(player).put("OverworldPosWhenVisitingMarket", NbtUtils.writeBlockPos(player.blockPosition().immutable()));
                        MarketGenerator.generate(targetLevel);
                        player.changeDimension(targetLevel, new SimpleTeleporter(new BlockPos(2, 61, 10)));
                        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 90, 0));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 63));
                        player.displayClientMessage(Component.translatable("message.feywild.market_condition"), false);
                        return true;
                    } else {
                        player.displayClientMessage(Component.translatable("message.feywild.market_closed"), false);
                    }
                }
            }
        } else {
            player.displayClientMessage(Component.translatable("message.feywild.market_wrong_source"), false);
        }
        return false;
    }

    public static boolean teleportToOverworld(ServerPlayer player) {
        if (player.level().dimension() == FeywildDimensions.MARKETPLACE) {
            ServerLevel targetLevel = player.level().getServer().overworld();
            BlockPos targetPos = FeyPlayerData.get(player).contains("OverworldPosWhenVisitingMarket", Tag.TAG_COMPOUND) ? NbtUtils.readBlockPos(FeyPlayerData.get(player).getCompound("OverworldPosWhenVisitingMarket")) : null;
            if (targetPos == null) {
                if (player.getRespawnDimension() == Level.OVERWORLD & player.getRespawnPosition() != null) {
                    targetPos = player.getRespawnPosition();
                } else {
                    targetPos = targetLevel.getSharedSpawnPos();
                }
            }
            if (targetPos == null) {
                // Should never happen
                targetPos = new BlockPos(0, 64, 0);
            }

            player.changeDimension(targetLevel, new SimpleTeleporter(targetPos));
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 90, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 63));
            return true;
        }
        return false;
    }
}
