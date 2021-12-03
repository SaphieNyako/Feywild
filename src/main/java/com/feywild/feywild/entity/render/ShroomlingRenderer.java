package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.Shroomling;
import com.feywild.feywild.entity.render.layer.ShroomlingLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ShroomlingRenderer<T extends Shroomling> extends GeoEntityRenderer<T> {

    protected ShroomlingRenderer(EntityRendererProvider.Context manager, AnimatedGeoModel<T> model) {
        super(manager, model);
        this.shadowRadius = 0.3F;
        this.addLayer(new ShroomlingLayer(this));
    }

    public static <T extends Shroomling> EntityRendererProvider<T> create(Supplier<AnimatedGeoModel<T>> modelProvider) {
        return manager -> new ShroomlingRenderer<>(manager, modelProvider.get());
    }

    @Override
    public void render(@Nonnull T entity, float entityYRot, float partialTicks, @Nonnull PoseStack stack, @Nonnull MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYRot, partialTicks, stack, bufferIn, packedLightIn);
    }
}
