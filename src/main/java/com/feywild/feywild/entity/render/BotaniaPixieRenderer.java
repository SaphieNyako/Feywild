package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.base.BotaniaPixie;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BotaniaPixieRenderer<T extends BotaniaPixie> extends GeoEntityRenderer<T> {

    private static final Vec3 OFFSET = new Vec3(0, -0.36, 0);
    
    public BotaniaPixieRenderer(EntityRendererProvider.Context manager, AnimatedGeoModel<T> model) {
        super(manager, model);
        this.shadowRadius = 0.01F;
    }

    public static <T extends BotaniaPixie> EntityRendererProvider<T> create(Supplier<AnimatedGeoModel<T>> model) {
        return manager -> new BotaniaPixieRenderer<>(manager, model.get());
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    @Nonnull
    @Override
    public Vec3 getRenderOffset(@Nonnull T entity, float partialTicks) {
        return OFFSET;
    }
}
