package com.feywild.feywild.entity.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class BaseEntityRenderer<T extends Entity & GeoAnimatable> extends GeoEntityRenderer<T> {
    
    public BaseEntityRenderer(EntityType<T> entityType, EntityRendererProvider.Context ctx, GeoModel<T> model) {
        super(ctx, model);
        this.shadowRadius = entityType.getWidth() * 0.75f;
    }
}
