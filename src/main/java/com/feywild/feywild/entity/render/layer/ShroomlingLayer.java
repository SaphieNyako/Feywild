package com.feywild.feywild.entity.render.layer;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Shroomling;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class ShroomlingLayer<T extends Shroomling> extends GeoLayerRenderer<T> {

    private static final ResourceLocation SHROOMLING_GLOW = FeywildMod.getInstance().resource("textures/entity/shroomling_glow.png");

    private static final ResourceLocation SHROOMLING = FeywildMod.getInstance().resource("geo/shroomling.geo.json");

    public ShroomlingLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType glow = RenderType.eyes(SHROOMLING_GLOW);
        this.getRenderer().render(this.getEntityModel().getModel(SHROOMLING), entityLivingBaseIn, partialTicks, glow,
                matrixStackIn, bufferIn, bufferIn.getBuffer(glow), packedLightIn, OverlayTexture.NO_OVERLAY, 0f, 0.3f, 0.9f, 1f
        );
    }
}
