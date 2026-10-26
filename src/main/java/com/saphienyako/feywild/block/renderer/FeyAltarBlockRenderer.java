package com.saphienyako.feywild.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.feywild.block.entity.FeyAltarBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FeyAltarBlockRenderer implements BlockEntityRenderer<@NotNull FeyAltarBlockEntity, @NotNull FeyAltarRenderState> {

    private final ItemModelResolver itemModelResolver;

    @SuppressWarnings("unused")
    public FeyAltarBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public void extractRenderState(FeyAltarBlockEntity altar, FeyAltarRenderState state, float partialTicks, @NotNull Vec3 cameraPosition, ModelFeatureRenderer.@NotNull CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(altar, state, partialTicks, cameraPosition, breakProgress);

        state.ingredients.clear();

        int lastSlot = altar.getInventory().getSlots() - 1;

        for (int slot = 0; slot < lastSlot; slot++) {
            ItemStack stack = altar.getInventory().getStackInSlot(slot);

            if (!stack.isEmpty()) {
                ItemStackRenderState itemState = new ItemStackRenderState();
                itemModelResolver.updateForTopItem(itemState, stack, ItemDisplayContext.GROUND, altar.getLevel(), null, slot);
                state.ingredients.add(itemState);
            }
        }

        ItemStack centerpiece = altar.getInventory().getStackInSlot(lastSlot);

        itemModelResolver.updateForTopItem(state.centerpiece, centerpiece, ItemDisplayContext.GROUND, altar.getLevel(), null, lastSlot);

        state.progress = altar.getMaxProgress() > 0 ? altar.getProgress() / (float) altar.getMaxProgress() : 0.0F;
        state.animationTime = altar.getLevel() != null ? altar.getLevel().getGameTime() + partialTicks : 0.0F;
    }

    @Override
    public FeyAltarRenderState createRenderState() {
        return new FeyAltarRenderState();
    }

    @Override
    public void submit(FeyAltarRenderState state, @NotNull PoseStack poseStack, @NotNull SubmitNodeCollector collector, @NotNull CameraRenderState cameraState) {
        double progressScaled = state.progress;
        double time = state.animationTime;

        if (!state.ingredients.isEmpty()) {

            double anglePerStack = (2 * Math.PI) / state.ingredients.size();

            for (int idx = 0; idx < state.ingredients.size(); idx++) {

                double shiftX = Math.cos((time / 8.0) + (idx * anglePerStack)) * (1 - progressScaled);
                double shiftZ = Math.sin((time / 8.0) + (idx * anglePerStack)) * (1 - progressScaled);

                poseStack.pushPose();
                poseStack.translate(0.5 + shiftX, 1.0 + progressScaled, 0.5 + shiftZ);
                poseStack.mulPose(Axis.YP.rotation((float) (time / 20.0)));
                poseStack.scale(0.85F, 0.85F, 0.85F);

                state.ingredients.get(idx).submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);

                poseStack.popPose();
            }
        }


        if (!state.centerpiece.isEmpty()) {

            poseStack.pushPose();

            double shiftY = Math.sin(time / 8.0) * 0.1;

            poseStack.translate(0.5, 2.0 + shiftY, 0.5);

            poseStack.mulPose(Axis.YP.rotation((float) (time / 8.0)));

            state.centerpiece.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);

            poseStack.popPose();
        }
    }


}
