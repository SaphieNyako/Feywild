package com.feywild.feywild.block.render;

import com.feywild.feywild.block.entity.MagicalBrazier;
import com.feywild.feywild.block.model.MagicalBrazierModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class MagicalBrazierRenderer extends GeoBlockRenderer<MagicalBrazier> {

    public MagicalBrazierRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new MagicalBrazierModel());
    }

    @Override
    public void render(MagicalBrazier tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
    }
}
