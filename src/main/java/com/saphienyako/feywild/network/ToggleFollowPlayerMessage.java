package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ToggleFollowPlayerMessage(int entityId, boolean followingPlayer, BlockPos currentBlockPos) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ToggleFollowPlayerMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "toggle_follow_player"));

    public static final StreamCodec<FriendlyByteBuf, ToggleFollowPlayerMessage> STREAM_CODEC =
            StreamCodec.of(ToggleFollowPlayerMessage::encode, ToggleFollowPlayerMessage::decode);

    private static void encode(FriendlyByteBuf buf, ToggleFollowPlayerMessage msg) {
        buf.writeInt(msg.entityId());
        buf.writeBoolean(msg.followingPlayer());
        buf.writeBlockPos(msg.currentBlockPos());
    }

    private static ToggleFollowPlayerMessage decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        boolean followingPlayer = buf.readBoolean();
        BlockPos pos = buf.readBlockPos();
        return new ToggleFollowPlayerMessage(id, followingPlayer, pos);
    }
    @SuppressWarnings("resource")
    public static void handle(ToggleFollowPlayerMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.level().isClientSide) return;

            Level level = player.level();
            if (msg.entityId() != -1) {
                FeyBase entity = (FeyBase) level.getEntity(msg.entityId());
                if (entity != null) {
                    entity.setFollowingPlayer(msg.followingPlayer());

                    if (!msg.followingPlayer()) {
                        player.sendSystemMessage(entity.getFeyStayMessage());
                        entity.setSummonPos(msg.currentBlockPos());
                    } else {
                        player.sendSystemMessage(entity.getFeyFollowMessage());
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
