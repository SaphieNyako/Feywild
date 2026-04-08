package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.entity.model.BeeKnightModel;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BeeKnightSpearLayer extends RenderLayer<BeeKnightEntity, BeeKnightModel<BeeKnightEntity>> {
    private final ItemRenderer itemRenderer;

    public BeeKnightSpearLayer(
            RenderLayerParent<BeeKnightEntity, BeeKnightModel<BeeKnightEntity>> parent,
            ItemRenderer itemRenderer
    ) {
        super(parent);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int packedLight,
            BeeKnightEntity entity,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {

        BeeMountEntity mount = entity.getMount();
        if (mount == null) return;

        ItemStack spear = ItemStack.EMPTY;

        if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_GOLD_SPEAR)) {
            spear = new ItemStack(ModItems.BEE_KNIGHT_GOLD_SPEAR.get());
        } else if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_DIAMOND_SPEAR)) {
            spear = new ItemStack(ModItems.BEE_KNIGHT_DIAMOND_SPEAR.get());
        } else if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_NETHERITE_SPEAR)) {
            spear = new ItemStack(ModItems.BEE_KNIGHT_NETHERITE_SPEAR.get());
        }

        if (spear.isEmpty()) return;

        poseStack.pushPose();

        this.getParentModel().getRightArm().translateAndRotate(poseStack);




        poseStack.translate(0.05F, 1.25F, 0.95F); //left right, forward backwards, up down
        poseStack.mulPose(Axis.XP.rotationDegrees(-130F)); // forward
        poseStack.mulPose(Axis.YP.rotationDegrees(160F)); // backwards


        itemRenderer.renderStatic(
                spear,
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId()
        );

        poseStack.popPose();
    }
}
