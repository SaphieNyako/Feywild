package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.SummerPixieEntity;
import com.feywild.feywild.entity.WinterPixieEntity;
import com.feywild.feywild.entity.model.SummerPixieModel;
import com.feywild.feywild.entity.model.WinterPixieModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WinterPixieRenderer extends GeoEntityRenderer<WinterPixieEntity> {

    public WinterPixieRenderer(EntityRendererManager renderManager) {

        super(renderManager, new WinterPixieModel());
        this.shadowSize = 0.2F;
    }
}
