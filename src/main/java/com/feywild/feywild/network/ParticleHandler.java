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
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
