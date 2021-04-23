package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.SummerPixieEntity;
import com.feywild.feywild.entity.model.SummerPixieModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;

public class SummerPixieRenderer extends BasePixieRenderer<SummerPixieEntity> {

    public SummerPixieRenderer(EntityRendererManager renderManager) {

        super(renderManager, new SummerPixieModel());
    }

    @Override
    protected BasicParticleType getParticleType() {
        return ParticleTypes.CRIT;
    }
}
