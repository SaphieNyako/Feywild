package com.feywild.feywild.block.render;

import com.feywild.feywild.block.AncientRunestoneBlock;
import com.feywild.feywild.block.entity.AncientRunestone;
import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.noeppi_noeppi.libx.render.ClientTickHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class AncientRunestoneRenderer extends TileEntityRenderer<AncientRunestone> {

    private final LazyValue<ItemStack> stack = new LazyValue<>(() -> {
        Item item = ForgeRegistries.ITEMS.getValue(AncientRunestoneBlock.NIDAVELLIR_RUNE);
        if (item == null) {
            return ItemStack.EMPTY;
        } else {
            return new ItemStack(item);
        }
    });
    
    public AncientRunestoneRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(@Nonnull AncientRunestone tile, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        if (tile.time() > 0) {
            matrixStack.pushPose();
            matrixStack.translate(0.5, 1.25, 0.5);
            double rot = 0.04 * (ClientTickHandler.ticksInGame + partialTicks);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees((float) (10 * Math.sin(rot))));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) (10 * Math.cos(rot))));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(90));
            matrixStack.scale(0.7f, 0.7f, 0.7f);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack.get(), ItemCameraTransforms.TransformType.FIXED, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, matrixStack, buffer);
            matrixStack.popPose();
        }
    }
}
