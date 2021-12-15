package com.feywild.feywild.entity.render.layer;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.ShroomlingEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class ShroomlingLayer<T extends ShroomlingEntity> extends GeoLayerRenderer<T> {

    private static final ResourceLocation SHROOMLING_GLOW = new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/shroomling_glow.png");
    private static final ResourceLocation SHROOMLING = new ResourceLocation(FeywildMod.getInstance().modid, "geo/shroomling.geo.json");
    private final IGeoRenderer<T> entityRenderer;

    public ShroomlingLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
        this.entityRenderer = entityRendererIn;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType glow = RenderType.eyes(SHROOMLING_GLOW);
        this.entityRenderer.render(this.getEntityModel().getModel(SHROOMLING), entitylivingbaseIn, partialTicks, glow,
                matrixStackIn, bufferIn, bufferIn.getBuffer(glow), packedLightIn, OverlayTexture.NO_OVERLAY, 0f, 0.3f, 0.9f, 1f);
    }

}
