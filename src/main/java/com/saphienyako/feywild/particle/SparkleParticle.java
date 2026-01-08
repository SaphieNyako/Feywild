package com.saphienyako.feywild.particle;


import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SparkleParticle extends SpriteTexturedParticle {

    public SparkleParticle(ClientWorld level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        //this.friction = 0.8f;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.quadSize *= 0.1F + ((float) Math.random() * 0.25f);
        this.lifetime = 20;
    }

    /*
    public static ParticleEngine.SpriteParticleRegistration<SimpleParticleType> provider(float red, float green, float blue) {
        return sprites -> (ParticleProvider<SimpleParticleType>) (type, level, x, y, z, xSpeed, ySpeed, zSpeed) -> {
            SparkleParticle particle = new SparkleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.setColor(red, green, blue);
            particle.pickSprite(sprites);
            return particle;
        };
    }

     */

    @Nonnull
    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    @Override
    public void tick() {
        super.tick();

        // friction
        this.xd *= 0.8F;
        this.yd *= 0.8F;
        this.zd *= 0.8F;

        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

    // ================= FACTORY =================

    public static class Factory implements IParticleFactory<BasicParticleType> {

        private final IAnimatedSprite sprites;
        private final float red, green, blue;

        public Factory(IAnimatedSprite sprites, float red, float green, float blue) {
            this.sprites = sprites;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        @Nullable
        @Override
        public Particle createParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {

            SparkleParticle particle = new SparkleParticle(
                    world, x, y, z, xSpeed, ySpeed, zSpeed
            );

            particle.setColor(red, green, blue);
            particle.setSpriteFromAge(this.sprites);
            return particle;
        }
    }

}
