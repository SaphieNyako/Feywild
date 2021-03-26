package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.SummerPixieEntity;
import com.feywild.feywild.entity.model.SpringPixieModel;
import com.feywild.feywild.entity.model.SummerPixieModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SummerPixieRenderer extends GeoEntityRenderer<SummerPixieEntity> {

    public SummerPixieRenderer(EntityRendererManager renderManager) {

        super(renderManager, new SummerPixieModel());
        this.shadowSize = 0.2F;
    }
}
