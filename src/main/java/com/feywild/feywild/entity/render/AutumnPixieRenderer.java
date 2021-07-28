package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.AutumnPixieEntity;
import com.feywild.feywild.entity.model.AutumnPixieModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;

public class AutumnPixieRenderer extends BasePixieRenderer<AutumnPixieEntity> {

    public AutumnPixieRenderer(EntityRendererManager renderManager) {
        super(renderManager, new AutumnPixieModel());
    }

    @Override
    protected BasicParticleType getParticleType() {
        return ParticleTypes.WITCH;
    }
}
