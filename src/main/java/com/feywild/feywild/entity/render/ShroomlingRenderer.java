package com.feywild.feywild.entity.render;

import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.entity.Shroomling;
import com.feywild.feywild.entity.render.layer.ShroomlingGlowLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.model.GeoModel;

public class ShroomlingRenderer<T extends Shroomling> extends BaseEntityRenderer<T> {

    public ShroomlingRenderer(EntityType<? extends T> entityType, EntityRendererProvider.Context ctx, GeoModel<T> model) {
        super(entityType, ctx, model);
        if (ClientConfig.mob_glow) this.addRenderLayer(new ShroomlingGlowLayer<>(this));
    }
}
