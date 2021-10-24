package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.base.FeyBase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BasePixieRenderer<T extends FeyBase> extends GeoEntityRenderer<T> {

    protected BasePixieRenderer(EntityRenderDispatcher manager, AnimatedGeoModel<T> model) {
        super(manager, model);
        this.shadowRadius = 0.2F;
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexConsumer, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
    
    public static <T extends FeyBase> IRenderFactory<T> create(Supplier<AnimatedGeoModel<T>> modelProvider) {
        return manager -> new BasePixieRenderer<>(manager, modelProvider.get());
    }
}

