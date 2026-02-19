package com.saphienyako.feywild.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class LeafParticleProvider implements ParticleProvider<SimpleParticleType> {

    private final SpriteSet spriteSet;

    public LeafParticleProvider(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Override
    public Particle createParticle(@NotNull SimpleParticleType type,
                                   @NotNull ClientLevel level,
                                   double x, double y, double z,
                                   double vx, double vy, double vz) {
        return new LeafParticle(level, x, y, z, vx, vy, vz, spriteSet);
    }
}