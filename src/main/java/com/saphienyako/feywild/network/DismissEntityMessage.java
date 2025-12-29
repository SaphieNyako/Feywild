package com.saphienyako.feywild.network;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public record DismissEntityMessage(int entityId) {

    public static void encode(DismissEntityMessage msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId());
    }

    public static DismissEntityMessage decode(FriendlyByteBuf buffer) {
        int id = buffer.readInt();

        return new DismissEntityMessage(id);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {

        Level level = supplier.get().getSender().level();
        if (this.entityId() != -1) {

            FeyBase entity = (FeyBase) level.getEntity(this.entityId);
            if(entity != null) {
                entity.spawnAtLocation(entity.getDismissItem());
                Objects.requireNonNull(supplier.get().getSender()).sendSystemMessage(entity.getFeyDismissMessage());
                if(ModConfig.CLIENT.voices_active.get() && entity.getVoiceActive()) {
                    FeywildNetwork.sendToPlayer(
                            new PlaySoundMessage(entity.getDismissSound().getLocation(), entity.blockPosition()),
                            supplier.get().getSender());
                }
                FeywildNetwork.sendParticles(level, ParticleMessage.Type.DANDELION_FLUFF, entity.blockPosition().above());
                entity.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }
}
