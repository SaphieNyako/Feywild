package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.saphienyako.feywild.entity.SpringPixieEntity;
import com.saphienyako.feywild.entity.model.SpringPixieModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class SpringPixieRenderer extends GeoEntityRenderer<SpringPixieEntity> {


    public SpringPixieRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SpringPixieModel());
        this.shadowRadius = 0.50f;
    }

    @Override
    public RenderType getRenderType(SpringPixieEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}
