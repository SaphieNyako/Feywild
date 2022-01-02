package com.feywild.feywild.world.dimension.market;

import com.feywild.feywild.FeyPlayerData;
import com.feywild.feywild.world.dimension.ModDimensions;
import com.feywild.feywild.world.dimension.SimpleTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

public class MarketHandler {

    public static void update(MinecraftServer server) {
        ServerLevel market = server.getLevel(ModDimensions.MARKET_PLACE_DIMENSION);
        if (market != null) {
            MarketData data = MarketData.get(market);
            if (data != null) {
                data.update(server, () -> market.getPlayers(player -> true).forEach(player -> {
                    teleportToOverworld(player);
                    player.displayClientMessage(new TranslatableComponent("message.feywild.market_closed"), false);
                }));
            }
        }
    }

    public static boolean teleportToMarket(ServerPlayer player) {
        if (player.getLevel().dimension() == Level.OVERWORLD) {
            ServerLevel targetLevel = player.getLevel().getServer().getLevel(ModDimensions.MARKET_PLACE_DIMENSION);
            if (targetLevel != null) {
                MarketData data = MarketData.get(targetLevel);
                if (data != null) {
                    if (data.isOpen()) {
                        FeyPlayerData.get(player).put("DwarfMarketPos", NbtUtils.writeBlockPos(player.blockPosition().immutable()));
                        MarketGenerator.generate(targetLevel);
                        player.changeDimension(targetLevel, new SimpleTeleporter(new BlockPos(2, 61, 10)));
                        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 63));
                        return true;
                    } else {
                        player.displayClientMessage(new TranslatableComponent("message.feywild.market_closed"), false);
                    }
                }
            }
        } else {
            player.displayClientMessage(new TranslatableComponent("message.feywild.market_wrong_source"), false);
        }
        return false;
    }

    public static boolean teleportToOverworld(ServerPlayer player) {
        if (player.getLevel().dimension() == ModDimensions.MARKET_PLACE_DIMENSION) {
            ServerLevel targetLevel = player.getLevel().getServer().overworld();
            BlockPos targetPos = FeyPlayerData.get(player).contains("DwarfMarketPos", Tag.TAG_COMPOUND) ? NbtUtils.readBlockPos(FeyPlayerData.get(player).getCompound("DwarfMarketPos")) : null;
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
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 63));
            return true;
        }
        return false;
    }
}
