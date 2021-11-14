package com.feywild.feywild.block.render;

import com.feywild.feywild.block.entity.MagicalBrazier;
import com.feywild.feywild.block.model.MagicalBrazierModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class MagicalBrazierRenderer extends GeoBlockRenderer<MagicalBrazier> {

    public MagicalBrazierRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher, new MagicalBrazierModel());
    }

    @Override
    public void render(MagicalBrazier tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
    }

    //TODO: Make Particles
}
