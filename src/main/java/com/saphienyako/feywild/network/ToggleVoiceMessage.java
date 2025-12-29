package com.saphienyako.feywild.network;

import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ToggleVoiceMessage(int entityId, boolean voiceActive) {

    public static void encode(ToggleVoiceMessage msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId());
        buf.writeBoolean(msg.voiceActive());
    }

    public static ToggleVoiceMessage decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        boolean voiceActive = buf.readBoolean();
        return new ToggleVoiceMessage(id, voiceActive);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        Level level = supplier.get().getSender().level;
        if (this.entityId() != -1) {
            FeyBase entity = (FeyBase) level.getEntity(this.entityId());
            if (entity != null) {
                entity.setVoiceActive(this.voiceActive);
            }
        }
    }
}
