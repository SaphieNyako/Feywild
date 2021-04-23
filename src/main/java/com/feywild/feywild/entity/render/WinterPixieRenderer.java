package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.WinterPixieEntity;
import com.feywild.feywild.entity.model.WinterPixieModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;

public class WinterPixieRenderer extends BasePixieRenderer<WinterPixieEntity> {

    public WinterPixieRenderer(EntityRendererManager renderManager) {
        super(renderManager, new WinterPixieModel());
    }

    @Override
    protected BasicParticleType getParticleType() {
        return ParticleTypes.ENCHANTED_HIT;
    }
}
