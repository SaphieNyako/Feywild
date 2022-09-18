package com.feywild.feywild.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

import javax.annotation.Nonnull;

public class SparkleParticle extends TextureSheetParticle {

    public SparkleParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.friction = 0.8f;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.quadSize *= 0.1F + ((float) Math.random() * 0.25f);
        this.lifetime = 20;
    }

    @Nonnull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }
    
    public static ParticleEngine.SpriteParticleRegistration<SimpleParticleType> provider(float red, float green, float blue) {
        return sprites -> (ParticleProvider<SimpleParticleType>) (type, level, x, y, z, xSpeed, ySpeed, zSpeed) -> {
            SparkleParticle particle = new SparkleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.setColor(red, green, blue);
            particle.pickSprite(sprites);
            return particle;
        };
    }
}
