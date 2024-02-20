package com.feywild.feywild.item.render;

import com.feywild.feywild.item.ReaperScythe;
import com.feywild.feywild.item.model.ReaperScytheModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ReaperScytheRenderer extends GeoItemRenderer<ReaperScythe> {

    public ReaperScytheRenderer() {
        super(new ReaperScytheModel());
    }
}
