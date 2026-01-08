package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.handler.AltarParticleMessageClientHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record AltarParticleMessage (Particles particles, BlockPos pos, int progress, int maxProgress) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<AltarParticleMessage> TYPE =
            new CustomPacketPayload.Type<>( ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "altar_particle")
            );


    public static final StreamCodec<FriendlyByteBuf, AltarParticleMessage> STREAM_CODEC =
            StreamCodec.of(AltarParticleMessage::encode, AltarParticleMessage::decode);

    private static void encode(FriendlyByteBuf buf, AltarParticleMessage msg) {
        buf.writeEnum(msg.particles());
        buf.writeBlockPos(msg.pos());
        buf.writeInt(msg.progress());
        buf.writeInt(msg.maxProgress());
    }

    private static AltarParticleMessage decode(FriendlyByteBuf buf) {
        Particles particles = buf.readEnum(Particles.class);
        BlockPos pos = buf.readBlockPos();
        int progress = buf.readInt();
        int maxProgress = buf.readInt();
        return new AltarParticleMessage(particles, pos, progress, maxProgress);
    }

    public static void handle(AltarParticleMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                AltarParticleMessageClientHandler.spawnParticles(msg);
            }
        });
    }


    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum Particles {
        ALTAR_01, ALTAR_02, ALTAR_03
    }
}



