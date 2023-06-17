package com.feywild.feywild.block.render;

import com.feywild.feywild.block.AncientRunestoneBlock;
import com.feywild.feywild.block.entity.AncientRunestone;
import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.render.ClientTickHandler;
import org.moddingx.libx.util.lazy.LazyValue;

import javax.annotation.Nonnull;

public class AncientRunestoneRenderer implements BlockEntityRenderer<AncientRunestone> {

    private final LazyValue<ItemStack> stack = new LazyValue<>(() -> {
        Item item = ForgeRegistries.ITEMS.getValue(AncientRunestoneBlock.NIDAVELLIR_RUNE);
        if (item == null) {
            return ItemStack.EMPTY;
        } else {
            return new ItemStack(item);
        }
    });

    @Override
    public void render(@Nonnull AncientRunestone tile, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int light, int overlay) {
        if (tile.time() > 0) {
            poseStack.pushPose();
            poseStack.translate(0.5, 1.25, 0.5);
            double rot = 0.04 * (ClientTickHandler.ticksInGame + partialTicks);
            poseStack.mulPose(Vector3f.XP.rotationDegrees((float) (10 * Math.sin(rot))));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees((float) (10 * Math.cos(rot))));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
            poseStack.scale(0.7f, 0.7f, 0.7f);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack.get(), ItemTransforms.TransformType.FIXED, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, poseStack, buffer, 0);
            poseStack.popPose();
        }
    }
}
