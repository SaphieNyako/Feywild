package com.feywild.feywild.entity.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.moddingx.libx.fi.Function3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BaseEntityRenderer<T extends Entity & GeoAnimatable> extends GeoEntityRenderer<T> {
    
    public BaseEntityRenderer(EntityType<? extends T> entityType, EntityRendererProvider.Context ctx, GeoModel<T> model) {
        super(ctx, model);
        this.shadowRadius = entityType.getWidth() * 0.75f;
    }
    
    public static <T extends Entity & GeoAnimatable> void register(EntityType<? extends T> entityType, GeoModel<T> model) {
        register(entityType, BaseEntityRenderer::new, model);
    }
    
    public static <T extends Entity & GeoAnimatable> void register(EntityType<? extends T> entityType, Function3<EntityType<? extends T>, EntityRendererProvider.Context, GeoModel<T>, ? extends BaseEntityRenderer<T>> factory, GeoModel<T> model) {
        EntityRenderers.register(entityType, ctx -> factory.apply(entityType, ctx, model));
    }
}
