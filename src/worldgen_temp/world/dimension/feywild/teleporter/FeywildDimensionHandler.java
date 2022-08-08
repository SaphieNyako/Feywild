package com.feywild.feywild.world.dimension.feywild.teleporter;

import com.feywild.feywild.FeyPlayerData;
import com.feywild.feywild.world.dimension.feywild.FeywildDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

public class FeywildDimensionHandler {

    public static boolean teleportToFeywild(ServerPlayer player) {
        if (player.getLevel().dimension() == Level.OVERWORLD) {
            ServerLevel targetLevel = player.getLevel().getServer().getLevel(FeywildDimension.FEYWILD_DIMENSION);
            if (targetLevel != null) {
                FeyPlayerData.get(player).put("OverworldPosition", NbtUtils.writeBlockPos(player.blockPosition().immutable()));
                player.changeDimension(targetLevel, new NoPortalTeleporter(new BlockPos(player.blockPosition())));
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 63));
                return true;
            }
        } else {
            player.displayClientMessage(new TranslatableComponent("message.feywild.market_wrong_source"), false);
        }
        return false;
    }

    public static boolean teleportToOverworld(ServerPlayer player) {
        if (player.getLevel().dimension() == FeywildDimension.FEYWILD_DIMENSION) {
            ServerLevel targetLevel = player.getLevel().getServer().overworld();
            BlockPos targetPos = FeyPlayerData.get(player).contains("OverworldPosition", Tag.TAG_COMPOUND) ? NbtUtils.readBlockPos(FeyPlayerData.get(player).getCompound("OverworldPosition")) : null;
            if (targetPos == null) {
                if (player.getRespawnDimension() == Level.OVERWORLD & player.getRespawnPosition() != null) {
                    targetPos = player.getRespawnPosition();
                } else {
                    targetPos = targetLevel.getSharedSpawnPos();
                }
            }
            player.changeDimension(targetLevel, new NoPortalTeleporter(targetPos));
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 63));
            return true;
        }
        return false;
    }
}
