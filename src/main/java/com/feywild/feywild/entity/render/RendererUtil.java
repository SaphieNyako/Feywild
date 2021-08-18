package com.feywild.feywild.entity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RendererUtil {

    public static RenderType glowRender(ResourceLocation texture) {
        RenderState.TextureState textureState = new RenderState.TextureState(texture, false, false);
        return RenderType.create("feywild_glow",
                DefaultVertexFormats.NEW_ENTITY,
                7,
                256,
                false,
                true,
                RenderType.State.builder().setTextureState(textureState).setTransparencyState(new RenderState.TransparencyState("feywild_transparency", () -> {
                    RenderSystem.enableBlend();
                    RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                }, () -> {
                    RenderSystem.disableBlend();
                    RenderSystem.defaultBlendFunc();
                })).createCompositeState(false));
    }
}
