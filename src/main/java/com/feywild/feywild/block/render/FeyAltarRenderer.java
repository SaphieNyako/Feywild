package com.feywild.feywild.block.render;

import com.feywild.feywild.block.entity.FeyAltar;
import com.feywild.feywild.block.model.FeyAltarModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.github.noeppi_noeppi.libx.render.ClientTickHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import java.util.ArrayList;
import java.util.List;

public class FeyAltarRenderer extends GeoBlockRenderer<FeyAltar> {

    public FeyAltarRenderer(BlockEntityRendererProvider.Context manager) {
        super(manager, new FeyAltarModel());
    }

    @Override
    public void render(FeyAltar tile, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        super.render(tile, partialTicks, poseStack, buffer, light);

        double progressScaled = tile.getProgress() / (double) FeyAltar.MAX_PROGRESS;

        List<ItemStack> stacks = new ArrayList<>();
        for (int slot = 0; slot < tile.getInventory().getSlots(); slot++) {
            ItemStack stack = tile.getInventory().getStackInSlot(slot);
            if (!stack.isEmpty()) stacks.add(stack);
        }

        if (!stacks.isEmpty()) {
            double anglePerStack = (2 * Math.PI) / stacks.size();
            for (int idx = 0; idx < stacks.size(); idx++) {
                //noinspection ConstantConditions
                double shiftX = Math.cos((((double) tile.getLevel().getGameTime() + partialTicks) / 8) + (idx * anglePerStack)) * (1 - progressScaled);
                double shiftZ = Math.sin((((double) tile.getLevel().getGameTime() + partialTicks) / 8) + (idx * anglePerStack)) * (1 - progressScaled);
                poseStack.pushPose();
                poseStack.translate(0.5 + shiftX, 1 + progressScaled, 0.5 + shiftZ);
                poseStack.mulPose(Vector3f.YP.rotation((ClientTickHandler.ticksInGame + partialTicks) / 20));
                poseStack.scale(0.85f, 0.85f, 0.85f);
                Minecraft.getInstance().getItemRenderer().renderStatic(stacks.get(idx), ItemTransforms.TransformType.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, buffer, 0);
                poseStack.popPose();
            }
        }
    }
}
