package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.MandragoraEntity;
import com.feywild.feywild.entity.model.MandragoraModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class MandragoraRenderer extends GeoEntityRenderer<MandragoraEntity> {

    public MandragoraRenderer(EntityRendererManager renderManager) {
        super(renderManager, new MandragoraModel());
    }

    @Override
    public RenderType getRenderType(MandragoraEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        //return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}
