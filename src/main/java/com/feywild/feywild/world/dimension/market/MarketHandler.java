package com.feywild.feywild.world.dimension.market;

import com.feywild.feywild.FeyPlayerData;
import com.feywild.feywild.world.dimension.ModDimensions;
import com.feywild.feywild.world.dimension.SimpleTeleporter;
import io.github.noeppi_noeppi.libx.util.NBTX;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MarketHandler {
    
    public static boolean teleportToMarket(ServerPlayerEntity player) {
        if (player.getLevel().dimension() == World.OVERWORLD) {
            ServerWorld targetLevel = player.getLevel().getServer().getLevel(ModDimensions.MARKET_PLACE_DIMENSION);
            if (targetLevel != null) {
                player.changeDimension(targetLevel, new SimpleTeleporter(new BlockPos(2, 61, 10)));
                player.addEffect(new EffectInstance(Effects.BLINDNESS, 60, 0));
                player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 63));
                return true;
            }
        }
        return false;
    }
    
    public static boolean teleportToOverworld(ServerPlayerEntity player) {
        if (player.getLevel().dimension() == ModDimensions.MARKET_PLACE_DIMENSION) {
            ServerWorld targetLevel = player.getLevel().getServer().overworld();
            BlockPos targetPos = NBTX.getPos(FeyPlayerData.get(player), "DwarfMarketPos", player.getRespawnPosition());
            player.changeDimension(targetLevel, new SimpleTeleporter(targetPos));
            player.addEffect(new EffectInstance(Effects.BLINDNESS, 60, 0));
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 63));
            return true;
        }
        return false;
    }
}
