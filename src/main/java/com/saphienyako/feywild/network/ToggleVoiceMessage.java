package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ToggleVoiceMessage(int entityId, boolean voiceActive) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ToggleVoiceMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "toggle_voice"));

    public static final StreamCodec<FriendlyByteBuf, ToggleVoiceMessage> STREAM_CODEC =
            StreamCodec.of(ToggleVoiceMessage::encode, ToggleVoiceMessage::decode);

    private static void encode(FriendlyByteBuf buf, ToggleVoiceMessage msg) {
        buf.writeInt(msg.entityId());
        buf.writeBoolean(msg.voiceActive());
    }

    private static ToggleVoiceMessage decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        boolean voiceActive = buf.readBoolean();
        return new ToggleVoiceMessage(id, voiceActive);
    }

    @SuppressWarnings("resource")
    public static void handle(ToggleVoiceMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.level().isClientSide) return;

            Level level = player.level();
            if (msg.entityId() != -1) {
                FeyBase entity = (FeyBase) level.getEntity(msg.entityId());
                if (entity != null) {
                    entity.setVoiceActive(msg.voiceActive);
                }
            }
        });
    }


    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;}
}
