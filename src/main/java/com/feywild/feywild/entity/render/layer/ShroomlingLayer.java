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

public class ShroomlingLayer extends GeoLayerRenderer<Shroomling> {

    // The resource location for a texture that matches the model texture in size but highlights the parts that will light.
    private static final ResourceLocation SHROOMLING_GLOW = new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/shroomling_glow");

    //The resource location for the geckolib model of the entity that has this layer.
    private static final ResourceLocation SHROOMLING = new ResourceLocation(FeywildMod.getInstance().modid, "geo/shroomling.geo.json");

    public ShroomlingLayer(IGeoRenderer<Shroomling> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Shroomling entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType glow = RenderType.eyes(SHROOMLING_GLOW);
        this.getRenderer().render(this.getEntityModel().getModel(SHROOMLING), entityLivingBaseIn, partialTicks, glow,
                matrixStackIn, bufferIn, bufferIn.getBuffer(glow), packedLightIn, OverlayTexture.NO_OVERLAY, 0.9f, 0.3f, 0f, 1f); //using example numbers for now
    }
}
