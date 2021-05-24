package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.entity.model.DwarfBlackSmithModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DwarfBlacksmithRenderer extends GeoEntityRenderer<DwarfBlacksmithEntity> {

    public DwarfBlacksmithRenderer(EntityRendererManager renderManager) {

        super(renderManager, new DwarfBlackSmithModel());
        this.shadowRadius = 0.8F;
    }

}
