package com.feywild.feywild.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LeafParticle extends TextureSheetParticle {
    
    private float move = 0;
    private final double initX;
    private final double velY;
    private final double initZ;
    private int remover;

    public LeafParticle(ClientLevel level, double x, double y, double z, double velX, double velY, double velZ) {
        super(level, x, y, z);
        this.setSize(0.5f,0.5f);
        this.alpha = 0;
        this.lifetime = (int)(10 / (Math.random() * 0.8D)) + 20;
        this.initX = x;
        this.initZ = z;
        this.velY = velY;
       this.remover = velX == velZ ? 1 : 0;
    }

    @Nonnull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        this.setPos(this.initX + Math.sin(this.move) * 2, this.y + velY, this.initZ + Math.cos(this.move) * 2 * remover);
        this.move += 0.1;
        if(this.lifetime == 0){
            this.remove();
        }
        this.lifetime--;
        if(this.move > 0.3)
            this.alpha = 1;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType>{

        private final SpriteSet sprite;
        public Factory(SpriteSet sprite){
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(@Nonnull SimpleParticleType type, @Nonnull ClientLevel level, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
            LeafParticle particle = new LeafParticle(level,p_199234_3_,p_199234_5_,p_199234_7_,p_199234_9_,p_199234_11_,p_199234_13_);
            particle.setColor(1,1,1);
            particle.pickSprite(this.sprite);
            return particle;
        }
    }
}


