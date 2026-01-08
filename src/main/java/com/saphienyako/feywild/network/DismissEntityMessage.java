package com.saphienyako.feywild.network;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;


public class DismissEntityMessage {

    private final int entityId;

    public DismissEntityMessage(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityId(){
        return entityId;
    }

    public static void encode(DismissEntityMessage msg, PacketBuffer buffer) {
        buffer.writeInt(msg.entityId);
    }

    public static DismissEntityMessage decode(PacketBuffer buffer) {
        int id = buffer.readInt();

        return new DismissEntityMessage(id);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        PlayerEntity player = supplier.get().getSender();
        World level = player.level;
        if (this.entityId != -1) {

            FeyBase entity = (FeyBase) level.getEntity(this.entityId);
            if(entity != null) {
                entity.spawnAtLocation(entity.getDismissItem());
                Objects.requireNonNull(player).sendMessage(entity.getFeyDismissMessage(), Objects.requireNonNull(player).getUUID());
                if(ModConfig.CLIENT.voices_active.get() && entity.getVoiceActive()) {
                    FeywildNetwork.sendToPlayer(
                            new PlaySoundMessage(entity.getDismissSound().getLocation(), entity.blockPosition()),
                            supplier.get().getSender());
                }
                FeywildNetwork.sendParticles(level, ParticleMessage.Type.DANDELION_FLUFF, entity.blockPosition().above());
                entity.remove();

            }
        }
    }

}
