package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.feywild.entity.MooShroomCowEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class MooShroomCowLayer <T extends MooShroomCowEntity> extends RenderLayer<T, CowModel<T>> {

    private final BlockRenderDispatcher blockRenderer;
    public MooShroomCowLayer(RenderLayerParent<T, CowModel<T>> layer, BlockRenderDispatcher blockRenderer) {
        super(layer);
        this.blockRenderer = blockRenderer;
    }

    public void render(@NotNull PoseStack poseStack,@NotNull MultiBufferSource source, int light, T cowEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!cowEntity.isBaby()) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean flag = minecraft.shouldEntityAppearGlowing(cowEntity) && cowEntity.isInvisible();
            if (!cowEntity.isInvisible() || flag) {
                BlockState blockstate = cowEntity.getMooShroomVariant().getShroomBlock().defaultBlockState();
                int i = LivingEntityRenderer.getOverlayCoords(cowEntity, 0.0F);
                BakedModel bakedmodel = this.blockRenderer.getBlockModel(blockstate);
                poseStack.pushPose();
                poseStack.translate(0.2F, -0.35F, 0.5F);
                poseStack.mulPose(Axis.YP.rotationDegrees(-48.0F));
                poseStack.scale(-1.0F, -1.0F, 1.0F);
                poseStack.translate(-0.5F, -0.5F, -0.5F);
                this.renderMushroomBlock(poseStack, source, light, flag, blockstate, i, bakedmodel);
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.translate(0.2F, -0.35F, 0.5F);
                poseStack.mulPose(Axis.YP.rotationDegrees(42.0F));
                poseStack.translate(0.1F, 0.0F, -0.6F);
                poseStack.mulPose(Axis.YP.rotationDegrees(-48.0F));
                poseStack.scale(-1.0F, -1.0F, 1.0F);
                poseStack.translate(-0.5F, -0.5F, -0.5F);
                this.renderMushroomBlock(poseStack, source, light, flag, blockstate, i, bakedmodel);
                poseStack.popPose();
                poseStack.pushPose();
                this.getParentModel().getHead().translateAndRotate(poseStack);
                poseStack.translate(0.0F, -0.7F, -0.2F);
                poseStack.mulPose(Axis.YP.rotationDegrees(-78.0F));
                poseStack.scale(-1.0F, -1.0F, 1.0F);
                poseStack.translate(-0.5F, -0.5F, -0.5F);
                this.renderMushroomBlock(poseStack, source, light, flag, blockstate, i, bakedmodel);
                poseStack.popPose();
            }
        }
    }

    private void renderMushroomBlock(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, boolean renderOutline, BlockState blockState, int packedOverlay, BakedModel bakedModel
    ) {
        if (renderOutline) {
            blockRenderer.getModelRenderer().renderModel(poseStack.last(), bufferSource.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), blockState, bakedModel, 0.0F, 0.0F, 0.0F, packedLight, packedOverlay);
        } else {
            blockRenderer.renderSingleBlock(blockState, poseStack, bufferSource, packedLight, packedOverlay);
        }
    }
}
