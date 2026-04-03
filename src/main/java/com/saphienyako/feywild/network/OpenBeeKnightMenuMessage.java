package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record OpenBeeKnightMenuMessage (int entityId) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<OpenBeeKnightMenuMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "open_bee_knight_menu"));

    public static final StreamCodec<FriendlyByteBuf, OpenBeeKnightMenuMessage> STREAM_CODEC =
            StreamCodec.of(OpenBeeKnightMenuMessage::encode, OpenBeeKnightMenuMessage::decode);

    private static void encode(FriendlyByteBuf buf, OpenBeeKnightMenuMessage msg) {
        buf.writeInt(msg.entityId());
    }

    private static OpenBeeKnightMenuMessage decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        return new OpenBeeKnightMenuMessage(id);
    }
    @SuppressWarnings("resource")
    public static void handle(OpenBeeKnightMenuMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.level().isClientSide) return;
            Level level = player.level();

            Entity entity = level.getEntity(msg.entityId);
            if (!(entity instanceof BeeMountEntity bee_mount)) return;
            bee_mount.openCustomInventoryScreen(player);
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
