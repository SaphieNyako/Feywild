package com.feywild.feywild.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.function.Supplier;

public record ParticleMessage(Type type, double x, double y, double z, double vx, double vy, double vz) {

    public enum Type {
        DANDELION_FLUFF, FEY_HEART, WIND_WALK, ANIMAL_BREED, MONSTER_FIRE, CROPS_GROW, SHROOMLING_SNEEZE, PIXIE_PARTICLES
    }

    public static class Serializer implements PacketSerializer<ParticleMessage> {

        @Override
        public Class<ParticleMessage> messageClass() {
            return ParticleMessage.class;
        }

        @Override
        public void encode(ParticleMessage msg, FriendlyByteBuf buffer) {
            buffer.writeEnum(msg.type());
            buffer.writeDouble(msg.x());
            buffer.writeDouble(msg.y());
            buffer.writeDouble(msg.z());
            buffer.writeDouble(msg.vx());
            buffer.writeDouble(msg.vy());
            buffer.writeDouble(msg.vz());
        }

        @Override
        public ParticleMessage decode(FriendlyByteBuf buffer) {
            Type type = buffer.readEnum(Type.class);
            double x = buffer.readDouble();
            double y = buffer.readDouble();
            double z = buffer.readDouble();
            double vx = buffer.readDouble();
            double vy = buffer.readDouble();
            double vz = buffer.readDouble();
            return new ParticleMessage(type, x, y, z, vx, vy, vz);
        }
    }

    public static class Handler implements PacketHandler<ParticleMessage> {

        @Override
        public Target target() {
            return Target.MAIN_THREAD;
        }

        @Override
        public boolean handle(ParticleMessage msg, Supplier<NetworkEvent.Context> ctx) {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                switch (msg.type()) {
                    case DANDELION_FLUFF -> {
                        for (int i = 0; i < 40; i++) {
                            level.addParticle(ParticleTypes.END_ROD, true, msg.x(), msg.y(), msg.z(), 0.6 * (level.random.nextDouble() - 0.5), 0.6 * (level.random.nextDouble() - 0.3), 0.6 * (level.random.nextDouble() - 0.5));
                        }
                    }
                    case FEY_HEART -> {
                        for (int i = 0; i < 5; i++) {
                            level.addParticle(ParticleTypes.HEART, true, msg.x() - 0.3 + (0.6 * level.random.nextDouble()), msg.y() + (0.6 * level.random.nextDouble()), msg.z() - 0.3 + (0.6 * level.random.nextDouble()), 0, 0, 0);
                        }
                    }
                    case WIND_WALK -> {
                        for (int i = 0; i < 10; i++) {
                            level.addParticle(ParticleTypes.WITCH, true, msg.x() - 0.5 + level.random.nextDouble(), msg.y() + 0.25 + level.random.nextDouble(), msg.z() - 0.5 + level.random.nextDouble(), 0, 0, 0);
                        }
                    }
                    case ANIMAL_BREED -> {
                        for (int i = 0; i < 10; i++) {
                            double x = msg.x() - 0.5 + level.random.nextDouble();
                            double y = msg.y() + level.random.nextDouble();
                            double z = msg.z() - 0.5 + level.random.nextDouble();
                            level.addParticle(ParticleTypes.HEART, true, x, y, z, (msg.vx() - x) * 0.11, (msg.vy() - y) * 0.11, (msg.vz() - z) * 0.11);
                        }
                    }
                    case MONSTER_FIRE -> {
                        for (int i = 0; i < 20; i++) {
                            double x = msg.x() - 0.5 + level.random.nextDouble();
                            double y = msg.y() + level.random.nextDouble();
                            double z = msg.z() - 0.5 + level.random.nextDouble();
                            level.addParticle(ParticleTypes.FLAME, true, x, y, z, (msg.vx() - x) * 0.15, (msg.vy() - y) * 0.15, (msg.vz() - z) * 0.15);
                        }
                    }
                    case CROPS_GROW -> {
                        for (int i = 0; i < 5; i++) {
                            level.addParticle(ParticleTypes.HAPPY_VILLAGER, true, msg.x() - 0.3 + (0.6 * level.random.nextDouble()), msg.y() + (0.3 * level.random.nextDouble()), msg.z() - 0.3 + (0.6 * level.random.nextDouble()), 0, 0, 0);
                        }
                    }
                    case SHROOMLING_SNEEZE -> {
                        for (int i = 0; i < 40; i++) {
                            level.addParticle(ParticleTypes.SNEEZE, true, msg.x(), msg.y(), msg.z(), 0.3 * (level.random.nextDouble() - 0.5), 0.3 * (level.random.nextDouble() - 0.3), 0.3 * (level.random.nextDouble() - 0.5));
                            level.addParticle(ParticleTypes.SPORE_BLOSSOM_AIR, true, msg.x(), msg.y(), msg.z(), 0.3 * (level.random.nextDouble() - 0.5), 0.3 * (level.random.nextDouble() - 0.3), 0.3 * (level.random.nextDouble() - 0.5));

                        }
                    }
                }
            }
            return true;
        }
    }
}
