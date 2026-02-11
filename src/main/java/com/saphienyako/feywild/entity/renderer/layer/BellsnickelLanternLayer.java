package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.entity.model.BellsnickelModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
@OnlyIn(Dist.CLIENT)
public class BellsnickelLanternLayer extends RenderLayer<BellsnickelEntity, BellsnickelModel<BellsnickelEntity>> {

    private final BlockRenderDispatcher blockRenderer;
    public BellsnickelLanternLayer(
            RenderLayerParent<BellsnickelEntity, BellsnickelModel<BellsnickelEntity>> parent,
            BlockRenderDispatcher blockRenderer
    ) {
        super(parent);
        this.blockRenderer = blockRenderer;
    }

    @Override
    public void render(@NotNull PoseStack poseStack,@NotNull MultiBufferSource buffer, int packedLight, BellsnickelEntity entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!entity.POSE_ANIMATION.isStarted()) return;
        poseStack.pushPose();
        this.getParentModel().getRightArm().translateAndRotate(poseStack);
        poseStack.translate(-0.1F, 0.52F, 1.55F); //left right, forward backwards, up down
        poseStack.translate(0.0F, 0.0F, 0.0F);
        poseStack.mulPose(Axis.XP.rotationDegrees(260.0F));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.translate(-0.5F, -0.5F, -0.5F);

        blockRenderer.renderSingleBlock(Blocks.SOUL_LANTERN.defaultBlockState(), poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }
}

