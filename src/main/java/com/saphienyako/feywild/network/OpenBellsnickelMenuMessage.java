package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record OpenBellsnickelMenuMessage(int entityId) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<OpenBellsnickelMenuMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "open_bellsnickel_menu"));

    public static final StreamCodec<FriendlyByteBuf, OpenBellsnickelMenuMessage> STREAM_CODEC =
            StreamCodec.of(OpenBellsnickelMenuMessage::encode, OpenBellsnickelMenuMessage::decode);

    private static void encode(FriendlyByteBuf buf, OpenBellsnickelMenuMessage msg) {
        buf.writeInt(msg.entityId());
    }

    private static OpenBellsnickelMenuMessage decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        return new OpenBellsnickelMenuMessage(id);
    }
    @SuppressWarnings("resource")
    public static void handle(OpenBellsnickelMenuMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.level().isClientSide) return;
            Level level = player.level();

            Entity entity = level.getEntity(msg.entityId);
            if (!(entity instanceof BellsnickelEntity bellsnickel)) return;
            bellsnickel.openCustomInventoryScreen(player);
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
