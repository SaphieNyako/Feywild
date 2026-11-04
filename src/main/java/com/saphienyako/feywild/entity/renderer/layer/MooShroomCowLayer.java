package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.feywild.entity.state.MooShroomCowRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MooShroomCowLayer extends RenderLayer<@NotNull MooShroomCowRenderState, @NotNull CowModel> {

    public MooShroomCowLayer(RenderLayerParent<@NotNull MooShroomCowRenderState, @NotNull CowModel> parent) {
        super(parent);
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int packedLight, MooShroomCowRenderState state, float yRot, float xRot) {
        if (state.isBaby || state.mushroomBlockState == null) return;

        boolean appearsGlowingWithInvisibility = state.appearsGlowing() && state.isInvisible;

        if (state.isInvisible && !appearsGlowingWithInvisibility) return;

        BlockState blockState = state.mushroomBlockState;

        BlockStateModel blockModel = Minecraft.getInstance()
                .getModelManager()
                .getBlockStateModelSet()
                .get(blockState);

        BlockModelRenderState mushroomModel = state.mushroomModel;
        mushroomModel.clear();

        RandomSource random = mushroomModel.scratchRandomSource(0L);

        List<BlockStateModelPart> parts = mushroomModel.setupModel(new Matrix4f(), false);

        blockModel.collectParts(BlockAndTintGetter.EMPTY, BlockPos.ZERO, blockState, random, parts);

        int overlayLocation = LivingEntityRenderer.getOverlayCoords(state, 0.0F);

        // First mushroom
        poseStack.pushPose();
        poseStack.translate(0.2F, -0.35F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-48.0F));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(-0.5F, -0.5F, -0.5F);

        submitMushroomBlock(poseStack, submitNodeCollector, packedLight, appearsGlowingWithInvisibility, state, overlayLocation);

        poseStack.popPose();

        // Second mushroom
        poseStack.pushPose();
        poseStack.translate(0.2F, -0.35F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(42.0F));
        poseStack.translate(0.1F, 0.0F, -0.6F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-48.0F));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(-0.5F, -0.5F, -0.5F);

        submitMushroomBlock(poseStack, submitNodeCollector, packedLight, appearsGlowingWithInvisibility, state, overlayLocation);

        poseStack.popPose();

        // Third mushroom (head)
        poseStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(poseStack);
        poseStack.translate(0.0F, -0.7F, -0.2F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-78.0F));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(-0.5F, -0.5F, -0.5F);

        submitMushroomBlock(poseStack, submitNodeCollector, packedLight, appearsGlowingWithInvisibility, state, overlayLocation);

        poseStack.popPose();
    }

    private void submitMushroomBlock(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, boolean renderOutline, MooShroomCowRenderState state, int overlayCoords) {
        if (renderOutline) {
            state.mushroomModel.submitOnlyOutline(poseStack, submitNodeCollector, packedLight, overlayCoords, state.outlineColor);
        } else {
            state.mushroomModel.submit(poseStack, submitNodeCollector, packedLight, overlayCoords, state.outlineColor);
        }
    }
}