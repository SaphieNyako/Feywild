package com.saphienyako.feywild.network;

import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleVoiceMessage {

    private final int entityId;
    private final boolean voiceActive;

    public ToggleVoiceMessage(int entityId, boolean voiceActive) {
        this.entityId = entityId;
        this.voiceActive = voiceActive;
    }

    public int getEntityId() {
        return entityId;
    }

    public boolean isVoiceActive() {
        return voiceActive;
    }

    public static void encode(ToggleVoiceMessage msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeBoolean(msg.voiceActive);
    }

    public static ToggleVoiceMessage decode(PacketBuffer buf) {
        int id = buf.readInt();
        boolean voiceActive = buf.readBoolean();
        return new ToggleVoiceMessage(id, voiceActive);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        World level = supplier.get().getSender().level;
        if (this.entityId != -1) {
            FeyBase entity = (FeyBase) level.getEntity(this.entityId);
            if (entity != null) {
                entity.setVoiceActive(this.voiceActive);
            }
        }
    }
}
