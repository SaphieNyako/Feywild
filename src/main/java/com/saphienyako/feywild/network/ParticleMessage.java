package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.handler.ParticleMessageClientHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;


public record ParticleMessage(Particles particles, BlockPos pos) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ParticleMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "particle")
            );

    public static final StreamCodec<FriendlyByteBuf, ParticleMessage> STREAM_CODEC =
            StreamCodec.of(
                    ParticleMessage::encode,
                    ParticleMessage::decode
            );

    private static void encode(FriendlyByteBuf buffer, ParticleMessage msg) {
        buffer.writeEnum(msg.particles);
        buffer.writeBlockPos(msg.pos);
    }

    private static ParticleMessage decode(FriendlyByteBuf buffer) {
        return new ParticleMessage(
                buffer.readEnum(Particles.class),
                buffer.readBlockPos()
        );
    }

    public static void handle(ParticleMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ParticleMessageClientHandler.spawnParticles(msg);
            }
        });
    }


    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }



    public enum Particles {
        DANDELION_FLUFF, FEY_HEART, CROPS_GROW, CROPS_RESET, MOB_COLLECT
    }
}
