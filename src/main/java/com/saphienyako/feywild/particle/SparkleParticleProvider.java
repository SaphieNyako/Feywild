package com.saphienyako.feywild.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class SparkleParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet spriteSet;
    private final float red, green, blue;

    public SparkleParticleProvider(SpriteSet spriteSet, float red, float green, float blue) {
        this.spriteSet = spriteSet;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel world,
                                   double x, double y, double z,
                                   double vx, double vy, double vz) {
        return new SparkleParticle(world, x, y, z, vx, vy, vz, spriteSet, red, green, blue);
    }
}
