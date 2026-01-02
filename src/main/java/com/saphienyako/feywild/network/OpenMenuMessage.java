package com.saphienyako.feywild.network;

import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.screen.FeyMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record OpenMenuMessage(Component name, int entityId, Alignment alignment, boolean followingPlayer, BlockPos currentBlockPos, boolean abilityActive, boolean voiceActive) {

    public static void encode(OpenMenuMessage msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId());
        buffer.writeComponent(msg.name);
        buffer.writeEnum(msg.alignment);
        buffer.writeBoolean(msg.followingPlayer);
        buffer.writeBlockPos(msg.currentBlockPos);
        buffer.writeBoolean(msg.abilityActive);
        buffer.writeBoolean(msg.voiceActive);
    }

    public static OpenMenuMessage decode(FriendlyByteBuf buffer) {
        int id = buffer.readInt();
        Component name = buffer.readComponent();
        Alignment alignment = buffer.readEnum(Alignment.class);
        boolean followingPlayer = buffer.readBoolean();
        BlockPos currentBlockPos = buffer.readBlockPos();
        boolean abilityActive = buffer.readBoolean();
        boolean voiceActive = buffer.readBoolean();

        return new OpenMenuMessage(name,id, alignment, followingPlayer,  currentBlockPos, abilityActive, voiceActive);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (this.entityId() != -1) {
                Minecraft.getInstance().setScreen(
                        new FeyMenuScreen(
                                this.name,
                                this.entityId,
                                this.alignment,
                                this.followingPlayer,
                                this.currentBlockPos,
                                this.abilityActive,
                                this.voiceActive
                        )
                );
            }
        });
        context.setPacketHandled(true);
    }

}
