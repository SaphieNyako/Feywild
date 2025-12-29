package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record DismissEntityMessage(int entityId) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<DismissEntityMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "dismiss_entity"));

    public static final StreamCodec<FriendlyByteBuf, DismissEntityMessage> STREAM_CODEC =
            StreamCodec.of(DismissEntityMessage::encode, DismissEntityMessage::decode);

    private static void encode(FriendlyByteBuf buf, DismissEntityMessage msg) {
        buf.writeInt(msg.entityId());
    }

    private static DismissEntityMessage decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        return new DismissEntityMessage(id);
    }
    @SuppressWarnings("resource")
    public static void handle(DismissEntityMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.level().isClientSide) return;

            Level level = player.level();
            if (!level.isClientSide && msg.entityId() != -1) {
                FeyBase entity = (FeyBase) level.getEntity(msg.entityId());
                if (entity != null) {
                    entity.spawnAtLocation(entity.getDismissItem());
                    player.sendSystemMessage(entity.getFeyDismissMessage());
                    if(FeywildConfig.voicesActive && entity.getVoiceActive()) {
                        PacketDistributor.sendToPlayersTrackingEntity(
                                entity,
                                new PlaySoundMessage(entity.getDismissSound(), entity.blockPosition())
                        );
                    }
                    PacketDistributor.sendToPlayersTrackingEntity(
                            entity,
                            new ParticleMessage(
                                    ParticleMessage.Particles.DANDELION_FLUFF,
                                    entity.blockPosition().above()
                            )
                    );
                    entity.remove(Entity.RemovalReason.DISCARDED);
                }
            }
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
