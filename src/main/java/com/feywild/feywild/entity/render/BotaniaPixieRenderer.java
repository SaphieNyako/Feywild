package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.BotaniaPixie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.model.GeoModel;

import javax.annotation.Nonnull;

public class BotaniaPixieRenderer<T extends BotaniaPixie> extends BaseEntityRenderer<T> {

    private static final Vec3 OFFSET = new Vec3(0, -0.36, 0);

    public BotaniaPixieRenderer(EntityType<? extends T> entityType, EntityRendererProvider.Context ctx, GeoModel<T> model) {
        super(entityType, ctx, model);
        this.shadowRadius = 0.01f;
    }

    @Nonnull
    @Override
    public Vec3 getRenderOffset(@Nonnull T entity, float partialTicks) {
        return OFFSET;
    }
}
