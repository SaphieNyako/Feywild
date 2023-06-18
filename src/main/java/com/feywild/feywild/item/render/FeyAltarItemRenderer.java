package com.feywild.feywild.item.render;

import com.feywild.feywild.item.FeyAltarItem;
import com.feywild.feywild.item.model.FeyAltarItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FeyAltarItemRenderer extends GeoItemRenderer<FeyAltarItem> {

    public FeyAltarItemRenderer() {
        super(new FeyAltarItemModel());
    }

}
