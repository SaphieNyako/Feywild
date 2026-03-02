package com.saphienyako.feywild.network;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record MountTreeEntMessage(int entityId){

    public static void encode(MountTreeEntMessage msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId());
    }

    public static MountTreeEntMessage decode(FriendlyByteBuf buf) {
        return new MountTreeEntMessage(buf.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
            Player player = supplier.get().getSender();
            Level level = player.level();
            if (level.isClientSide) return;

            if (this.entityId() != -1) {
                Entity entity = level.getEntity(this.entityId());
                if (entity instanceof TreeEntBase treeEnt) {

                    if (treeEnt.canRide(player) && !treeEnt.isVehicle()) {
                        player.startRiding(treeEnt, true);
                    }
                    if(ModConfig.COMMON.voice_active.get() && treeEnt.getVoiceActive()) {
                        level.playSound(
                                null,
                                treeEnt.blockPosition(),
                                treeEnt.getFollowSound(),
                                SoundSource.NEUTRAL,
                                1.0F,
                                1.0F
                        );
                    }
                }
            }
    }
}
