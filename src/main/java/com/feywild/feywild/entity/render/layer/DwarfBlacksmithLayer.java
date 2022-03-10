package com.feywild.feywild.entity.render.layer;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class DwarfBlacksmithLayer<T extends DwarfBlacksmithEntity> extends GeoLayerRenderer<T> {

    private static final ResourceLocation BLACKSMITH_GLOW = new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/dwarf_blacksmith_glow.png");
    private static final ResourceLocation BLACKSMITH_CANDLE = new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/dwarf_blacksmith_candle.png");
    private static final ResourceLocation BLACKSMITH = new ResourceLocation(FeywildMod.getInstance().modid, "geo/dwarf_blacksmith.geo.json");

    private final IGeoRenderer<T> entityRenderer;

    public DwarfBlacksmithLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
        this.entityRenderer = entityRendererIn;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType glow = RenderType.eyes(BLACKSMITH_GLOW);
        RenderType candle = RenderType.eyes(BLACKSMITH_CANDLE);
        this.entityRenderer.render(this.getEntityModel().getModel(BLACKSMITH), entitylivingbaseIn, partialTicks, glow,
                matrixStackIn, bufferIn, bufferIn.getBuffer(glow), packedLightIn, OverlayTexture.NO_OVERLAY, 0f, 0.3f, 0.9f, 0.5f);
        this.entityRenderer.render(this.getEntityModel().getModel(BLACKSMITH), entitylivingbaseIn, partialTicks, candle,
                matrixStackIn, bufferIn, bufferIn.getBuffer(glow), packedLightIn, OverlayTexture.NO_OVERLAY, 0.3f, 0.3f, 0.3f, 0.8f);
    }
}
