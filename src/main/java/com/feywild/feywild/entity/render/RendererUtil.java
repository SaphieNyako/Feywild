package com.feywild.feywild.entity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.resources.ResourceLocation;

// TODO can we delete this?
public class RendererUtil {

    public static RenderType glowRender(ResourceLocation texture) {
        RenderStateShard.TextureStateShard textureState = new RenderStateShard.TextureStateShard(texture, false, false);
        return RenderType.create("feywild_glow",
                DefaultVertexFormat.NEW_ENTITY,
                7,
                256,
                false,
                true,
                RenderType.CompositeState.builder().setTextureState(textureState).setTransparencyState(new RenderStateShard.TransparencyStateShard("feywild_transparency", () -> {
                    RenderSystem.enableBlend();
                    RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                }, () -> {
                    RenderSystem.disableBlend();
                    RenderSystem.defaultBlendFunc();
                })).createCompositeState(false));
    }
}
