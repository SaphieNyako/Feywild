package com.feywild.feywild.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;

import javax.annotation.Nonnull;

public abstract class SparkleParticleBase extends TextureSheetParticle {

    protected SparkleParticleBase(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.friction = 0.8f;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.quadSize *= 0.1F + ((float) Math.random() * 0.25f);
        this.lifetime = 20;
    }

    @Override
    public @Nonnull
    ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.lifetime == 0) {
            this.remove();
        }
        this.lifetime--;
    }


}
