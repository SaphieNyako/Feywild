package com.feywild.feywild.entity.render.layer;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.DwarfBlacksmith;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class DwarfBlacksmithLayer<T extends DwarfBlacksmith> extends GeoLayerRenderer<T> {

    private static final ResourceLocation BLACKSMITH_GLOW = FeywildMod.getInstance().resource("textures/entity/dwarf_blacksmith_glow.png");
    private static final ResourceLocation BLACKSMITH_CANDLE = FeywildMod.getInstance().resource("textures/entity/dwarf_blacksmith_candle.png");
    private static final ResourceLocation BLACKSMITH = FeywildMod.getInstance().resource("geo/dwarf_blacksmith.geo.json");

    public DwarfBlacksmithLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType glow = RenderType.eyes(BLACKSMITH_GLOW);
        RenderType candle = RenderType.eyes(BLACKSMITH_CANDLE);
        this.getRenderer().render(this.getEntityModel().getModel(BLACKSMITH), entityLivingBaseIn, partialTicks, glow,
                matrixStackIn, bufferIn, bufferIn.getBuffer(glow), packedLightIn, OverlayTexture.NO_OVERLAY, 0f, 0.3f, 0.9f, 0.5f);
        this.getRenderer().render(this.getEntityModel().getModel(BLACKSMITH), entityLivingBaseIn, partialTicks, candle,
                matrixStackIn, bufferIn, bufferIn.getBuffer(glow), packedLightIn, OverlayTexture.NO_OVERLAY, 0.3f, 0.3f, 0.3f, 0.8f);
    }
}
