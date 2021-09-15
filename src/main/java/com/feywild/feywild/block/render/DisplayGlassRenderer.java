package com.feywild.feywild.block.render;

import com.feywild.feywild.block.entity.DisplayGlassEntity;
import com.feywild.feywild.block.entity.FeyAltar;
import com.feywild.feywild.block.model.FeyAltarModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.noeppi_noeppi.libx.render.ClientTickHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import java.util.ArrayList;
import java.util.List;

public class DisplayGlassRenderer extends TileEntityRenderer<DisplayGlassEntity> {

    public DisplayGlassRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }


    @Override
    public void render(DisplayGlassEntity pBlockEntity, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, int pCombinedOverlay) {

        ItemStack stack = pBlockEntity.getInventory().getStackInSlot(0);

        if(!stack.isEmpty()) {
            pMatrixStack.pushPose();
            pMatrixStack.translate(0.5, 0.5, 0.5);
            pMatrixStack.mulPose(Vector3f.YP.rotation((ClientTickHandler.ticksInGame + pPartialTicks) / 20));
            pMatrixStack.scale(0.85f, 0.85f, 0.85f);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemCameraTransforms.TransformType.GROUND, pCombinedLight, OverlayTexture.NO_OVERLAY, pMatrixStack, pBuffer);
            pMatrixStack.popPose();
        }
    }
}
