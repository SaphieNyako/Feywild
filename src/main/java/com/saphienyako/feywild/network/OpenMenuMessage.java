package com.saphienyako.feywild.network;

import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.screen.FeyMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenMenuMessage {

    private final ITextComponent name;
    private final int entityId;
    private final Alignment alignment;
    private final boolean followingPlayer;
    private final BlockPos currentBlockPos;
    private final boolean abilityActive;
    private final boolean voiceActive;

    public OpenMenuMessage(ITextComponent name, int entityId, Alignment alignment,
                           boolean followingPlayer, BlockPos currentBlockPos,
                           boolean abilityActive, boolean voiceActive) {
        this.name = name;
        this.entityId = entityId;
        this.alignment = alignment;
        this.followingPlayer = followingPlayer;
        this.currentBlockPos = currentBlockPos;
        this.abilityActive = abilityActive;
        this.voiceActive = voiceActive;
    }

    public ITextComponent getName() { return name; }
    public int getEntityId() { return entityId; }
    public Alignment getAlignment() { return alignment; }
    public boolean isFollowingPlayer() { return followingPlayer; }
    public BlockPos getCurrentBlockPos() { return currentBlockPos; }
    public boolean isAbilityActive() { return abilityActive; }
    public boolean isVoiceActive() { return voiceActive; }

    public static void encode(OpenMenuMessage msg, PacketBuffer buffer) {
        buffer.writeInt(msg.entityId);
        buffer.writeComponent(msg.name);
        buffer.writeEnum(msg.alignment);
        buffer.writeBoolean(msg.followingPlayer);
        buffer.writeBlockPos(msg.currentBlockPos);
        buffer.writeBoolean(msg.abilityActive);
        buffer.writeBoolean(msg.voiceActive);
    }

    public static OpenMenuMessage decode(PacketBuffer buffer) {
        int id = buffer.readInt();
        ITextComponent name = buffer.readComponent();
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
            if (this.entityId != -1) {
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
