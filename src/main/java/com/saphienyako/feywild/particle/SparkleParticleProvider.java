package com.saphienyako.feywild.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class SparkleParticleProvider implements ParticleProvider<@NotNull SimpleParticleType> {
    private final SpriteSet spriteSet;
    private final float red, green, blue;

    public SparkleParticleProvider(SpriteSet spriteSet, float red, float green, float blue) {
        this.spriteSet = spriteSet;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double vx, double vy, double vz, @NotNull RandomSource random) {
        return new SparkleParticle(level, x, y, z, vx, vy, vz, spriteSet, red, green, blue);
    }
}
