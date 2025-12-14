package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
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
            Level level = Minecraft.getInstance().level;
            if (level == null) return;

            RandomSource ran = level.random;

            switch (msg.particles) {
                case DANDELION_FLUFF -> {
                    for (int i = 0; i < 40; i++) {
                        level.addParticle(
                                ParticleTypes.END_ROD,
                                true,
                                msg.pos().getX(),
                                msg.pos().getY(),
                                msg.pos().getZ(),
                                0.6 * (ran.nextDouble() - 0.5),
                                0.6 * (ran.nextDouble() - 0.3),
                                0.6 * (ran.nextDouble() - 0.5)
                        );
                    }
                }

                case FEY_HEART -> {
                    double y = msg.pos().getY() + 1;
                    for (int i = 0; i < 5; i++) {
                        level.addParticle(
                                ParticleTypes.HEART,
                                true,
                                msg.pos().getX() - 0.3 + 0.6 * ran.nextDouble(),
                                y + 1.9 * ran.nextDouble(),
                                msg.pos().getZ() - 0.3 + 0.6 * ran.nextDouble(),
                                0, 0, 0
                        );
                    }
                }

                case CROPS_GROW -> {
                    for (int i = 0; i < 10; i++) {
                        level.addParticle(
                                ParticleTypes.FLAME,
                                true,
                                msg.pos().getX() - 0.3 + 0.6 * ran.nextDouble(),
                                msg.pos().getY() + 0.8 * ran.nextDouble(),
                                msg.pos().getZ() - 0.3 + 0.6 * ran.nextDouble(),
                                0, 0, 0
                        );
                    }
                }

                case MOB_COLLECT -> {
                    for (int i = 0; i < 20; i++) {
                        level.addParticle(
                                ParticleTypes.SOUL_FIRE_FLAME,
                                true,
                                msg.pos().getX() - 0.3 + 0.9 * ran.nextDouble(),
                                msg.pos().getY() + 1.2 * ran.nextDouble(),
                                msg.pos().getZ() - 0.3 + 0.9 * ran.nextDouble(),
                                0, 0, 0
                        );
                    }
                }

                case CROPS_RESET -> {
                    for (int i = 0; i < 10; i++) {
                        level.addParticle(
                                ParticleTypes.GLOW,
                                true,
                                msg.pos().getX() - 0.3 + 0.6 * ran.nextDouble(),
                                msg.pos().getY() + 0.8 * ran.nextDouble(),
                                msg.pos().getZ() - 0.3 + 0.6 * ran.nextDouble(),
                                0, 0, 0
                        );
                    }
                }
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
