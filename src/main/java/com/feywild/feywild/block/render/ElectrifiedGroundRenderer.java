package com.feywild.feywild.block.render;

import com.feywild.feywild.block.entity.ElectrifiedGroundTileEntity;
import com.feywild.feywild.block.model.ElectrifiedGroundModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class ElectrifiedGroundRenderer extends GeoBlockRenderer<ElectrifiedGroundTileEntity> {
    
    public ElectrifiedGroundRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new ElectrifiedGroundModel());
    }
}
