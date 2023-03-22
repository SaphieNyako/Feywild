package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.base.TreeEntBase;
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

public class BaseTreeEntRenderer<T extends TreeEntBase> extends GeoEntityRenderer<T> {

    public BaseTreeEntRenderer(EntityRendererProvider.Context manager, AnimatedGeoModel<T> model) {
        super(manager, model);
        this.shadowRadius = 2.0F;
    }


    public static <T extends TreeEntBase> EntityRendererProvider<T> create(Supplier<AnimatedGeoModel<T>> modelProvider) {
        return manager -> new BaseTreeEntRenderer<>(manager, modelProvider.get());
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexConsumer, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

}
