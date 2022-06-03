package com.feywild.feywild.entity.render;

import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.entity.Shroomling;
import com.feywild.feywild.entity.render.layer.ShroomlingLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ShroomlingRenderer<T extends Shroomling> extends GeoEntityRenderer<T> {

    protected ShroomlingRenderer(EntityRendererProvider.Context manager, AnimatedGeoModel<T> model) {
        super(manager, model);
        this.shadowRadius = 0.3F;
        if (ClientConfig.mob_glow) {
            this.addLayer(new ShroomlingLayer<>(this));
        }
    }

    public static <T extends Shroomling> EntityRendererProvider<T> create(Supplier<AnimatedGeoModel<T>> modelProvider) {
        return manager -> new ShroomlingRenderer<>(manager, modelProvider.get());
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexConsumer, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}
