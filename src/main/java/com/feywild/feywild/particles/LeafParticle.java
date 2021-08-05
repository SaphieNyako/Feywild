package com.feywild.feywild.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

// TODO
@OnlyIn(Dist.CLIENT)
public class LeafParticle extends SpriteTexturedParticle {
    float move = 0;
    double initX, initY, initZ;
    protected LeafParticle(ClientWorld p_i232447_1_, double p_i232447_2_, double p_i232447_4_, double p_i232447_6_) {
        super(p_i232447_1_, p_i232447_2_, p_i232447_4_, p_i232447_6_);
        this.setSize(0.5f,0.5f);
        this.alpha = 0;
        this.lifetime = (int)(10 / (Math.random() * 0.8D)) + 20;
        this.initX = p_i232447_2_;
        this.initY = p_i232447_4_;
        this.initZ = p_i232447_6_;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        setPos(initX + Math.sin(move) * 2,y-0.04,initZ + Math.cos(move) * 2);
        move+= 0.1;
        if(lifetime == 0){
            this.remove();
        }
        lifetime--;
        if(move > 0.3)
        alpha = 1;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType>{

        private final IAnimatedSprite sprite;
        public Factory(IAnimatedSprite sprite){
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(BasicParticleType p_199234_1_, ClientWorld p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
            LeafParticle particle = new LeafParticle(p_199234_2_,p_199234_3_,p_199234_5_,p_199234_7_);
            particle.setColor(1,1,1);
            particle.pickSprite(sprite);
            return particle;
        }
    }
}


