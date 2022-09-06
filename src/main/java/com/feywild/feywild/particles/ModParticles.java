package com.feywild.feywild.particles;


import net.minecraft.core.particles.SimpleParticleType;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "PARTICLE_TYPE_REGISTRY")
public class ModParticles {

    public static final SimpleParticleType leafParticle = new SimpleParticleType(true);
    public static final SimpleParticleType springLeafParticle = new SimpleParticleType(true);
    public static final SimpleParticleType summerLeafParticle = new SimpleParticleType(true);
    public static final SimpleParticleType winterLeafParticle = new SimpleParticleType(true);
}
