package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.PatchouliAPI;

public record OpenPatchouliBookMessage(Identifier bookId) implements CustomPacketPayload {

    public static final Type<@NotNull OpenPatchouliBookMessage> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "open_patchouli_book"));

    public static final StreamCodec<FriendlyByteBuf, OpenPatchouliBookMessage> STREAM_CODEC =
            StreamCodec.of(OpenPatchouliBookMessage::encode, OpenPatchouliBookMessage::decode);

    private static void encode(FriendlyByteBuf buf, OpenPatchouliBookMessage msg) {
        buf.writeIdentifier(msg.bookId());
    }

    private static OpenPatchouliBookMessage decode(FriendlyByteBuf buf) {
        return new OpenPatchouliBookMessage(buf.readIdentifier());
    }

    public static void handle(OpenPatchouliBookMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();

            if (player instanceof ServerPlayer serverPlayer) {
                PatchouliAPI.get().openBookGUI(serverPlayer, msg.bookId());
            }
        });
    }

    @Override
    public @NotNull Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}
