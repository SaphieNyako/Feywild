package com.feywild.feywild.world.dimension.market;

import com.feywild.feywild.FeyPlayerData;
import com.feywild.feywild.world.dimension.ModDimensions;
import com.feywild.feywild.world.dimension.SimpleTeleporter;
import io.github.noeppi_noeppi.libx.util.NBTX;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MarketHandler {
    
    public static void update(MinecraftServer server) {
        ServerWorld market = server.getLevel(ModDimensions.MARKET_PLACE_DIMENSION);
        if (market != null) {
            MarketData data = MarketData.get(market);
            if (data != null) {
                data.update(server, () -> market.getPlayers(player -> true).forEach(player -> {
                    teleportToOverworld(player);
                    player.displayClientMessage(new TranslationTextComponent("message.feywild.market_closed"), false);
                }));
            }
        }
    }
    
    public static boolean teleportToMarket(ServerPlayerEntity player) {
        if (player.getLevel().dimension() == World.OVERWORLD) {
            ServerWorld targetLevel = player.getLevel().getServer().getLevel(ModDimensions.MARKET_PLACE_DIMENSION);
            if (targetLevel != null) {
                MarketData data = MarketData.get(targetLevel);
                if (data != null) {
                    if (data.isOpen()) {
                        NBTX.putPos(FeyPlayerData.get(player), "DwarfMarketPos", player.blockPosition().immutable());
                        MarketGenerator.generate(targetLevel);
                        player.changeDimension(targetLevel, new SimpleTeleporter(new BlockPos(2, 61, 10)));
                        player.addEffect(new EffectInstance(Effects.BLINDNESS, 60, 0));
                        player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 63));
                        return true;
                    } else {
                        player.displayClientMessage(new TranslationTextComponent("message.feywild.market_closed"), false);
                    }
                }
            }
        } else {
            player.displayClientMessage(new TranslationTextComponent("message.feywild.market_wrong_source"), false);
        }
        return false;
    }
    
    public static boolean teleportToOverworld(ServerPlayerEntity player) {
        if (player.getLevel().dimension() == ModDimensions.MARKET_PLACE_DIMENSION) {
            ServerWorld targetLevel = player.getLevel().getServer().overworld();
            BlockPos targetPos = NBTX.getPos(FeyPlayerData.get(player), "DwarfMarketPos", null);
            if (targetPos == null) {
                if (player.getRespawnDimension() == World.OVERWORLD & player.getRespawnPosition() != null) {
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
            player.addEffect(new EffectInstance(Effects.BLINDNESS, 60, 0));
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 63));
            return true;
        }
        return false;
    }
}
