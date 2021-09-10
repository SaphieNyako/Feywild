package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.BeeKnight;
import com.feywild.feywild.entity.SummerPixieEntity;
import com.feywild.feywild.entity.model.BeeKnightModel;
import com.feywild.feywild.entity.model.SummerPixieModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;

public class BeeKnightRenderer extends BasePixieRenderer<BeeKnight> {

    public BeeKnightRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BeeKnightModel());
    }

    @Override
    protected BasicParticleType getParticleType() {
        return ParticleTypes.CRIT;
    }
}
