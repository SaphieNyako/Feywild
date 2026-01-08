package com.saphienyako.feywild.block.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.saphienyako.feywild.block.entity.ClientTickHandler;
import com.saphienyako.feywild.block.entity.FeyAltarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
@OnlyIn(Dist.CLIENT)
public class FeyAltarBlockRenderer<T extends FeyAltarBlockEntity> extends TileEntityRenderer<FeyAltarBlockEntity> {



    public FeyAltarBlockRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(@Nonnull FeyAltarBlockEntity altar, float partialTicks,@Nonnull MatrixStack matrixStack,@Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        double progressScaled = altar.getProgress() / (double) altar.getMaxProgress();

        List<ItemStack> stacks = new ArrayList<>();
        int lastSlot = altar.getInventory().getSlots() - 1;

        for (int slot = 0; slot < lastSlot; slot++) {
            ItemStack stack = altar.getInventory().getStackInSlot(slot);
            if (!stack.isEmpty()) stacks.add(stack);
        }

        if (!stacks.isEmpty()) {
            double anglePerStack = (2 * Math.PI) / stacks.size();
            for (int idx = 0; idx < stacks.size(); idx++) {
                double shiftX = Math.cos(((altar.getLevel().getGameTime() + partialTicks) / 8) + (idx * anglePerStack)) * (1 - progressScaled);
                double shiftZ = Math.sin(((altar.getLevel().getGameTime() + partialTicks) / 8) + (idx * anglePerStack)) * (1 - progressScaled);
                GlStateManager._pushMatrix();


                GlStateManager._pushMatrix();
                GlStateManager._translated(shiftX + 0.5 + shiftX, progressScaled, 0.5 + shiftZ);
                GlStateManager._rotatef((ClientTickHandler.ticksInGame() + partialTicks) / 20f, 0, 1, 0);
                GlStateManager._scalef(0.85f, 0.85f, 0.85f);

                Minecraft.getInstance().getItemRenderer().renderStatic(stacks.get(idx), ItemCameraTransforms.TransformType.GROUND, light, overlay, matrixStack, buffer );

                GlStateManager._popMatrix();
            }

            // Render the centerpiece (last slot item) above the altar
            ItemStack centerpiece = altar.getInventory().getStackInSlot(lastSlot);
            if (!centerpiece.isEmpty()) {
                GlStateManager._pushMatrix();
                double time = altar.getLevel().getGameTime() + partialTicks;
                double amplitude = 0.1;
                double shiftY = Math.sin(time / 8) * amplitude;

                GlStateManager._translated(0.5, 2.0 + shiftY, 0.5);
                GlStateManager._rotatef((float) (time / 8), 0, 1, 0);
                GlStateManager._scalef(1f, 1f, 1f);

                Minecraft.getInstance().getItemRenderer().renderStatic(centerpiece, ItemCameraTransforms.TransformType.GROUND, light, overlay, matrixStack, buffer);

                GlStateManager._popMatrix();
            }

        }
    }
}
