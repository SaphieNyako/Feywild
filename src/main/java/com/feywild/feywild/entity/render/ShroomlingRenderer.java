package com.feywild.feywild.entity.render;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Shroomling;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ShroomlingRenderer<T extends Shroomling> extends GeoEntityRenderer<T> {

    private final EntityModel<T> model;

    protected ShroomlingRenderer(EntityRendererProvider.Context manager, AnimatedGeoModel<T> model) {
        super(manager, model);
        this.shadowRadius = 0.3F;
    }

    public static <T extends Shroomling> EntityRendererProvider<T> create(Supplier<AnimatedGeoModel<T>> modelProvider) {
        return manager -> new ShroomlingRenderer<>(manager, modelProvider.get());
    }

    @Override
    public void render(@Nonnull T entity, float entityYRot, float partialTicks, @Nonnull PoseStack stack, @Nonnull MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYRot, partialTicks, stack, bufferIn, packedLightIn);
        if (entity.level.isNight()) {
            renderGlowLayer(entity, entityYRot, partialTicks, stack, bufferIn, packedLightIn);
        }

    }

    private void renderGlowLayer(Shroomling shroomling, float entityYRot, float partialTicks, @Nonnull PoseStack stack, @Nonnull MultiBufferSource bufferIn, int packedLightIn) {
        ResourceLocation glowLayerLocation = new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/shroomling_glow");
        VertexConsumer consumer = bufferIn.getBuffer(RenderType.eyes(glowLayerLocation));
        model.renderToBuffer(stack, consumer, packedLightIn, OverlayTexture.NO_OVERLAY, 101, 189, 206, 1.0F);
        //  stack.pushPose();
        //  stack.translate();
        //  stack.scale();
        //  stack.popPose();

    }
}
