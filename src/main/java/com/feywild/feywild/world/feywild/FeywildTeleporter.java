package com.feywild.feywild.world.feywild;

import com.feywild.feywild.world.FeywildDimensions;
import com.feywild.feywild.world.teleport.DefaultTeleporter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

public class FeywildTeleporter {

    public static boolean teleportToFeywild(ServerPlayer player) {
        if (player.getLevel().dimension() == Level.OVERWORLD) {
            ServerLevel targetLevel = player.getLevel().getServer().getLevel(FeywildDimensions.FEYWILD);
            if (targetLevel != null) {
                player.changeDimension(targetLevel, new DefaultTeleporter());
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 90, 0));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 63));
                return true;
            }
        } else {
            player.displayClientMessage(Component.translatable("message.feywild.feywild_wrong_source"), false);
        }
        return false;
    }

    public static boolean teleportToOverworld(ServerPlayer player) {
        if (player.getLevel().dimension() == FeywildDimensions.FEYWILD) {
            ServerLevel targetLevel = player.getLevel().getServer().overworld();
            player.changeDimension(targetLevel, new DefaultTeleporter());
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 90, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 63));
            return true;
        }
        return false;
    }
}
