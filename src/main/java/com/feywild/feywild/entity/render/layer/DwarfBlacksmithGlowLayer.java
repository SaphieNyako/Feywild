package com.feywild.feywild.entity.render.layer;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.DwarfBlacksmith;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class DwarfBlacksmithGlowLayer<T extends DwarfBlacksmith> extends GeoRenderLayer<T> {

    private static final ResourceLocation BLACKSMITH_GLOW = FeywildMod.getInstance().resource("textures/entity/dwarf_blacksmith_glow.png");
    private static final ResourceLocation BLACKSMITH_CANDLE = FeywildMod.getInstance().resource("textures/entity/dwarf_blacksmith_candle.png");

    public DwarfBlacksmithGlowLayer(GeoRenderer<T> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, T dwarf, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int light, int overlay) {
        RenderType glow = RenderType.eyes(BLACKSMITH_GLOW);
        RenderType candle = RenderType.eyes(BLACKSMITH_CANDLE);
        this.getRenderer().actuallyRender(poseStack, dwarf, model, glow, bufferSource, buffer, false, partialTick, light, overlay, 1, 1, 1, 1);
        this.getRenderer().actuallyRender(poseStack, dwarf, model, candle, bufferSource, buffer, false, partialTick, light, overlay, 1, 1, 1, 1);
    }
}
