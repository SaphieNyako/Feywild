package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.entity.model.SpringPixieModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;

public class SpringPixieRenderer extends BasePixieRenderer<SpringPixieEntity> {

    public SpringPixieRenderer(EntityRendererManager renderManager) {

        super(renderManager, new SpringPixieModel());
    }

    @Override
    public void render(SpringPixieEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    //TODO Fix particle problem pixies after opening menu

    @Override
    protected BasicParticleType getParticleType() {
        return ParticleTypes.HAPPY_VILLAGER;
    }
}
