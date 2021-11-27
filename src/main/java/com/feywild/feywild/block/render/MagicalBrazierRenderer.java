package com.feywild.feywild.block.render;

import com.feywild.feywild.block.entity.MagicalBrazier;
import com.feywild.feywild.block.model.MagicalBrazierModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class MagicalBrazierRenderer extends GeoBlockRenderer<MagicalBrazier> {

    public MagicalBrazierRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new MagicalBrazierModel());
    }
}
