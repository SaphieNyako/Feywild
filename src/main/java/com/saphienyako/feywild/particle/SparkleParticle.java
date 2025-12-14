package com.saphienyako.feywild.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import org.jetbrains.annotations.NotNull;

public class SparkleParticle extends TextureSheetParticle {
    protected SparkleParticle(ClientLevel world, double x, double y, double z,
                              double vx, double vy, double vz,
                              SpriteSet spriteSet, float red, float green, float blue) {
        super(world, x, y, z, vx, vy, vz);
        this.pickSprite(spriteSet);

        this.rCol = red;
        this.gCol = green;
        this.bCol = blue;

        this.quadSize = 0.05f + (float)Math.random() * 0.05f;
        this.lifetime = 20;
        this.friction = 0.85f;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age >= this.lifetime) {
            this.remove();
        }
    }
}
