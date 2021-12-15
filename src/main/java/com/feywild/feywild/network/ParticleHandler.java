package com.feywild.feywild.network;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ParticleHandler {

    public static void handle(ParticleSerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            World world = Minecraft.getInstance().level;
            if (world != null) {
                switch (msg.type) {
                    case DANDELION_FLUFF:
                        for (int i = 0; i < 40; i++) {
                            world.addParticle(ParticleTypes.END_ROD, true, msg.x, msg.y, msg.z, 0.6 * (world.random.nextDouble() - 0.5), 0.6 * (world.random.nextDouble() - 0.3), 0.6 * (world.random.nextDouble() - 0.5));
                        }
                        break;
                    case FEY_HEART:
                        for (int i = 0; i < 5; i++) {
                            world.addParticle(ParticleTypes.HEART, true, msg.x - 0.3 + (0.6 * world.random.nextDouble()), msg.y + (0.6 * world.random.nextDouble()), msg.z - 0.3 + (0.6 * world.random.nextDouble()), 0, 0, 0);
                        }
                        break;
                    case WIND_WALK:
                        for (int i = 0; i < 10; i++) {
                            world.addParticle(ParticleTypes.WITCH, true, msg.x - 0.5 + world.random.nextDouble(), msg.y + 0.25 + world.random.nextDouble(), msg.z - 0.5 + world.random.nextDouble(), 0, 0, 0);
                        }
                        break;
                    case ANIMAL_BREED:
                        for (int i = 0; i < 10; i++) {
                            double x = msg.x - 0.5 + world.random.nextDouble();
                            double y = msg.y + world.random.nextDouble();
                            double z = msg.z - 0.5 + world.random.nextDouble();
                            world.addParticle(ParticleTypes.TOTEM_OF_UNDYING, true, x, y, z, (msg.vx - x) * 0.11, (msg.vy - y) * 0.11, (msg.vz - z) * 0.11);
                        }
                        break;
                    case MONSTER_FIRE:
                        for (int i = 0; i < 20; i++) {
                            double x = msg.x - 0.5 + world.random.nextDouble();
                            double y = msg.y + world.random.nextDouble();
                            double z = msg.z - 0.5 + world.random.nextDouble();
                            world.addParticle(ParticleTypes.FLAME, true, x, y, z, (msg.vx - x) * 0.15, (msg.vy - y) * 0.15, (msg.vz - z) * 0.15);
                        }
                        break;
                    case CROPS_GROW:
                        for (int i = 0; i < 5; i++) {
                            world.addParticle(ParticleTypes.HAPPY_VILLAGER, true, msg.x - 0.3 + (0.6 * world.random.nextDouble()), msg.y + (0.3 * world.random.nextDouble()), msg.z - 0.3 + (0.6 * world.random.nextDouble()), 0, 0, 0);
                        }
                        break;
                    case SHROOMLING_SNEEZE:
                        for (int i = 0; i < 40; i++) {
                            world.addParticle(ParticleTypes.SNEEZE, true, msg.x, msg.y, msg.z, 0.3 * (world.random.nextDouble() - 0.5), 0.3 * (world.random.nextDouble() - 0.3), 0.3 * (world.random.nextDouble() - 0.5));
                        }
                        break;
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
