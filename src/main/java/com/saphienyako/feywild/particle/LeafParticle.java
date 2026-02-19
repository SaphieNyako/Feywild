package com.saphienyako.feywild.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import org.jetbrains.annotations.NotNull;

public class LeafParticle extends TextureSheetParticle {
    private final double initX;
    private final double initZ;
    private final double velY;
    private final int remover;

    private float move = 0;

    protected LeafParticle(ClientLevel level,
                           double x, double y, double z,
                           double velX, double velY, double velZ,
                           SpriteSet sprite) {
        super(level, x, y, z);

        this.initX = x;
        this.initZ = z;
        this.velY = velY;
        this.remover = velX == velZ ? 1 : 0;

        this.setSize(0.5F, 0.5F);
        this.alpha = 0.0F;
        this.lifetime = (int) (10 / (Math.random() * 0.8D)) + 20;

        this.pickSprite(sprite);
        this.setColor(1.0F, 1.0F, 1.0F);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();

        this.setPos(
                this.initX + Math.sin(this.move) * 2.0D,
                this.y + this.velY,
                this.initZ + Math.cos(this.move) * 2.0D * this.remover
        );

        this.move += 0.1F;

        if (this.move > 0.3F) {
            this.alpha = 1.0F;
        }

        if (this.age >= this.lifetime) {
            this.remove();
        }
    }
}
