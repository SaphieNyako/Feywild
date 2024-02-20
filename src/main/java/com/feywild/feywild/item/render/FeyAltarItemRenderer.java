package com.feywild.feywild.item.render;

import com.feywild.feywild.block.model.FeyAltarModel;
import com.feywild.feywild.item.FeyAltarItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FeyAltarItemRenderer extends GeoItemRenderer<FeyAltarItem> {

    public FeyAltarItemRenderer() {
        super(new FeyAltarModel<>());
    }
}
