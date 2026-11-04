package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.feywild.entity.model.BeeKnightModel;
import com.saphienyako.feywild.entity.state.BeeKnightRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;


@OnlyIn(Dist.CLIENT)
public class BeeKnightSpearLayer extends RenderLayer<@NotNull BeeKnightRenderState, @NotNull BeeKnightModel> {

    private final ItemModelResolver itemModelResolver;
    private final ItemStackRenderState itemRenderState = new ItemStackRenderState();

    public BeeKnightSpearLayer(RenderLayerParent<@NotNull BeeKnightRenderState, @NotNull BeeKnightModel> renderer, ItemModelResolver itemModelResolver) {
        super(renderer);
        this.itemModelResolver = itemModelResolver;
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int packedLight, BeeKnightRenderState state, float limbSwing, float limbSwingAmount) {
        if (state.spear.isEmpty() || state.isInvisible) {
            return;
        }

        this.itemModelResolver.updateForTopItem(this.itemRenderState, state.spear, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, null, null, 0);

        poseStack.pushPose();

        this.getParentModel().getRightArm().translateAndRotate(poseStack);

        poseStack.translate(0.05F, 1.25F, 0.95F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-130F));
        poseStack.mulPose(Axis.YP.rotationDegrees(160F));

        this.itemRenderState.submit(poseStack, submitNodeCollector, packedLight, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
    }
}