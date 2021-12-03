package com.feywild.feywild.entity.render.layer;

public class RendererUtil {

 /*   public static RenderType glowRender(ResourceLocation texture) {

        RenderStateShard.TextureStateShard textureStateShard = new RenderStateShard.TextureStateShard(texture, false, false);
        return RenderType.create("feywild_glow",
                DefaultVertexFormat.NEW_ENTITY,
                7,
                256,
                false,
                true,

                RenderType.CompositeState.builder().setTextureState(textureStateShard).setTransparencyState(new RenderStateShard("feywild_transparency",
                        () -> {
                            RenderSystem.enableBlend();
                            RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        }, () -> {
                    RenderSystem.disableBlend();
                    RenderSystem.defaultBlendFunc();
                })).createCompositeState(false));
    }

    }*/
}
