package com.saphienyako.feywild.network.handler;

import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class ParticleMessageClientHandler {
    public static void spawnParticles(ParticleMessage msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        RandomSource ran = level.random;

        switch (msg.particles()) {
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
    }
}
