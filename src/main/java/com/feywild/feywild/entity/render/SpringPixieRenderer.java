package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.entity.model.SpringPixieModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SpringPixieRenderer extends GeoEntityRenderer <SpringPixieEntity>{

    public SpringPixieRenderer(EntityRendererManager renderManager) {

        super(renderManager, new SpringPixieModel());
        this.shadowSize = 0.2F;
    }

    @Override
    public void render(SpringPixieEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }
}
