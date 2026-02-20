package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record MountTreeEntMessage(int entityId) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MountTreeEntMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "ride_tree_ent"));

    public static final StreamCodec<FriendlyByteBuf, MountTreeEntMessage> STREAM_CODEC =
            StreamCodec.of(MountTreeEntMessage::encode, MountTreeEntMessage::decode);

    private static void encode(FriendlyByteBuf buf, MountTreeEntMessage msg) {
        buf.writeInt(msg.entityId());
    }

    private static MountTreeEntMessage decode(FriendlyByteBuf buf) {
        return new MountTreeEntMessage(buf.readInt());
    }

    public static void handle(MountTreeEntMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Level level = player.level();
            if (level.isClientSide) return;

            if (msg.entityId() != -1) {
                Entity entity = level.getEntity(msg.entityId());
                if (entity instanceof TreeEntBase treeEnt) {

                    if (treeEnt.canRide(player) && !treeEnt.isVehicle()) {
                        player.startRiding(treeEnt, true);
                    }
                }
            }
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
