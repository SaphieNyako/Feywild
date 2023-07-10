package com.feywild.feywild.entity.render;

import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.entity.DwarfBlacksmith;
import com.feywild.feywild.entity.render.layer.DwarfBlacksmithGlowLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.model.GeoModel;

public class DwarfBlacksmithRenderer<T extends DwarfBlacksmith> extends BaseEntityRenderer<T> {

    public DwarfBlacksmithRenderer(EntityType<? extends T> entityType, EntityRendererProvider.Context ctx, GeoModel<T> model) {
        super(entityType, ctx, model);
        if (ClientConfig.mob_glow) this.addRenderLayer(new DwarfBlacksmithGlowLayer<>(this));
    }
}
