package com.feywild.feywild.block.render;

import com.feywild.feywild.block.entity.DisplayGlass;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.moddingx.libx.render.ClientTickHandler;

import javax.annotation.Nonnull;

public class DisplayGlassRenderer implements BlockEntityRenderer<DisplayGlass> {

    @Override
    public void render(DisplayGlass tile, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int light, int overlay) {
        ItemStack stack = tile.getInventory().getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.mulPose(Axis.YP.rotation((ClientTickHandler.ticksInGame() + partialTicks) / 20f));
            poseStack.scale(0.85f, 0.85f, 0.85f);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, buffer, tile.getLevel(), 0);
            poseStack.popPose();
        }
    }
}
