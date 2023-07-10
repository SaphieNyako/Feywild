package com.feywild.feywild.item.render;

import com.feywild.feywild.item.FeyWing;
import com.feywild.feywild.item.model.FeyWingsModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class FeyWingsRenderer extends GeoArmorRenderer<FeyWing> {
    
    public FeyWingsRenderer(FeyWing.Variant variant) {
        super(new FeyWingsModel(variant));
    }
}
