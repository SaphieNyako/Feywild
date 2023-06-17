package com.feywild.feywild.item.render;


import com.feywild.feywild.item.ReaperScythe;
import com.feywild.feywild.item.model.ReaperScytheModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ReaperScytheRenderer extends GeoItemRenderer<ReaperScythe> {

    public ReaperScytheRenderer() {
        super(new ReaperScytheModel());
    }
}
