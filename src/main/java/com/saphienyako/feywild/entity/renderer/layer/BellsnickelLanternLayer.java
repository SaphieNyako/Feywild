package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.feywild.entity.model.BellsnickelModel;
import com.saphienyako.feywild.entity.state.BellsnickelRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BellsnickelLanternLayer extends RenderLayer<@NotNull BellsnickelRenderState, @NotNull BellsnickelModel> {

    private static final BlockState LANTERN_STATE = Blocks.SOUL_LANTERN.defaultBlockState();

    private final BlockModelRenderState lanternModel = new BlockModelRenderState();
    private final BlockStateModel lanternBlockModel;

    public BellsnickelLanternLayer(RenderLayerParent<@NotNull BellsnickelRenderState, @NotNull BellsnickelModel> parent) {
        super(parent);

        this.lanternBlockModel = Minecraft.getInstance()
                .getModelManager()
                .getBlockStateModelSet()
                .get(LANTERN_STATE);
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int packedLight, BellsnickelRenderState state, float limbSwing, float limbSwingAmount) {
        if (!state.poseAnimation.isStarted() || state.isInvisible) return;

        this.lanternModel.clear();
        RandomSource random = this.lanternModel.scratchRandomSource(0L);

        List<BlockStateModelPart> parts = this.lanternModel.setupModel(new Matrix4f(), false);

        this.lanternBlockModel.collectParts(BlockAndTintGetter.EMPTY, BlockPos.ZERO, LANTERN_STATE, random, parts);
        poseStack.pushPose();

        this.getParentModel().getRightArm().translateAndRotate(poseStack);

        poseStack.translate(-0.1F, 0.52F, 1.55F);
        poseStack.mulPose(Axis.XP.rotationDegrees(260.0F));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.translate(-0.5F, -0.5F, -0.5F);

        this.lanternModel.submit(poseStack, submitNodeCollector, packedLight, LivingEntityRenderer.getOverlayCoords(state, 0.0F), state.outlineColor);

        poseStack.popPose();
    }
}

