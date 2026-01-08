package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.network.handler.OpenMenuMessageClientHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record OpenMenuMessage(int entityId, Alignment alignment, boolean followingPlayer, BlockPos currentBlockPos, boolean abilityActive, boolean voiceActive) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<OpenMenuMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "open_menu"));

    public static final StreamCodec<FriendlyByteBuf, OpenMenuMessage> STREAM_CODEC =
            StreamCodec.of(OpenMenuMessage::encode, OpenMenuMessage::decode);

    private static void encode(FriendlyByteBuf buf, OpenMenuMessage msg) {
        buf.writeInt(msg.entityId());
        buf.writeEnum(msg.alignment());
        buf.writeBoolean(msg.followingPlayer());
        buf.writeBlockPos(msg.currentBlockPos());
        buf.writeBoolean(msg.abilityActive());
        buf.writeBoolean(msg.voiceActive());
    }

    private static OpenMenuMessage decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        Alignment alignment = buf.readEnum(Alignment.class);
        boolean followingPlayer = buf.readBoolean();
        BlockPos currentBlockPos = buf.readBlockPos();
        boolean abilityActive = buf.readBoolean();
        boolean voiceActive = buf.readBoolean();
        return new OpenMenuMessage(id, alignment, followingPlayer, currentBlockPos, abilityActive, voiceActive);
    }

    public static void handle(OpenMenuMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                OpenMenuMessageClientHandler.openMenu(msg);
            }
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
