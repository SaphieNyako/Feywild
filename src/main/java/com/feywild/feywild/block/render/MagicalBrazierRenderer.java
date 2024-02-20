package com.feywild.feywild.block.render;

import com.feywild.feywild.block.entity.MagicalBrazier;
import com.feywild.feywild.block.model.MagicalBrazierModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MagicalBrazierRenderer extends GeoBlockRenderer<MagicalBrazier> {

    public MagicalBrazierRenderer() {
        super(new MagicalBrazierModel());
    }
}
