package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.ShroomlingEntity;
import com.feywild.feywild.entity.render.layer.ShroomlingLayer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.function.Supplier;

public class ShroomlingRenderer<T extends ShroomlingEntity> extends GeoEntityRenderer<T> {

    protected ShroomlingRenderer(EntityRendererManager manager, AnimatedGeoModel<T> model) {
        super(manager, model);
        this.shadowRadius = 0.3F;
        this.addLayer(new ShroomlingLayer<>(this));
    }

    public static <T extends ShroomlingEntity> IRenderFactory<T> create(Supplier<AnimatedGeoModel<T>> modelProvider) {
        return manager -> new ShroomlingRenderer<>(manager, modelProvider.get());
    }

}
