package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.entity.model.SpringPixieModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;

public class SpringPixieRenderer extends BasePixieRenderer<SpringPixieEntity> {

    public SpringPixieRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SpringPixieModel());
    }

    @Override
    protected BasicParticleType getParticleType() {
        return ParticleTypes.HAPPY_VILLAGER;
    }
}
