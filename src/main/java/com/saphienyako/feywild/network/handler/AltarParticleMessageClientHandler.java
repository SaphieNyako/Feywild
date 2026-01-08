package com.saphienyako.feywild.network.handler;

import com.saphienyako.feywild.network.AltarParticleMessage;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class AltarParticleMessageClientHandler {

    public static void spawnParticles(AltarParticleMessage msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        RandomSource random = level.random;

        switch (msg.particles()) {
            case ALTAR_01 -> {
                for (int i = 0; i < 20; i++) {
                    double x = msg.pos().getX() + 0.5;
                    double y = msg.pos().getY() + 1.2;
                    double z = msg.pos().getZ() + 0.5;

                    level.addParticle(ModParticles.FEY_SPARKLE_PARTICLE.get(), true,
                            x, y, z,
                            0.5 - random.nextDouble(), 0.7 - random.nextDouble(), 0.5 - random.nextDouble());


                    level.addParticle(ParticleTypes.END_ROD, true,
                            x, y, z,
                            0.5 - random.nextDouble(), 0.7 - random.nextDouble(), 0.5 - random.nextDouble());
                }
            }

            case ALTAR_02 -> {
                double progressScaled = msg.progress() / (double) msg.maxProgress();
                double anglePerStack = (2 * Math.PI) / 5;

                for (int idx = 0; idx < 5; idx++) {
                    double shiftX = Math.cos((level.getGameTime() / 8.0) + (idx * anglePerStack)) * (1 - progressScaled);
                    double shiftZ = Math.sin((level.getGameTime() / 8.0) + (idx * anglePerStack)) * (1 - progressScaled);
                    double x = msg.pos().getX() + 0.5 + shiftX;
                    double y = msg.pos().getY() + 1 + progressScaled;
                    double z = msg.pos().getZ() + 0.5 + shiftZ;

                    level.addParticle(ModParticles.FEY_SPARKLE_PARTICLE.get(), true, x, y, z, 0, 0, 0);
                    level.addParticle(ParticleTypes.END_ROD, true, x, y, z, 0, 0, 0);
                }
            }

            case ALTAR_03 -> {
                if (random.nextFloat() < 0.1) {
                    double x = msg.pos().getX() + Math.random();
                    double y = msg.pos().getY() + 1 + Math.random();
                    double z = msg.pos().getZ() + Math.random();
                    level.addParticle(ModParticles.FEY_SPARKLE_PARTICLE.get(), true, x, y, z, 0, 0, 0);
                }
                if (random.nextFloat() < 0.02) {
                    double x = msg.pos().getX() + Math.random();
                    double y = msg.pos().getY() + 1 + Math.random();
                    double z = msg.pos().getZ() + Math.random();
                    level.addParticle(ParticleTypes.END_ROD, true, x, y, z, 0, 0, 0);
                }
            }
        }
    }
}
