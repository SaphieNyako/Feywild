package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.entity.model.BeeKnightModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BeeKnightArmorLayer extends RenderLayer<BeeKnightEntity, BeeKnightModel<BeeKnightEntity>> {

    private final BeeKnightModel<BeeKnightEntity> armorModel;

    public BeeKnightArmorLayer(RenderLayerParent<BeeKnightEntity, BeeKnightModel<BeeKnightEntity>> parent,
                              EntityModelSet modelSet) {
        super(parent);
        this.armorModel = new BeeKnightModel<>(modelSet.bakeLayer(ModModelLayers.BEE_KNIGHT_LAYER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, BeeKnightEntity entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        BeeMountEntity mount = entity.getMount();
        if (mount == null) return;

        ResourceLocation texture = null;

        if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_GOLD_ARMOR)) {
            texture = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                    "textures/entity/bee_knight/bee_knight_gold_armor.png");
        } else if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_DIAMOND_ARMOR)) {
            texture = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                    "textures/entity/bee_knight/bee_knight_diamond_armor.png");
        } else if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_NETHERITE_ARMOR)) {
            texture = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                    "textures/entity/bee_knight/bee_knight_netherite_armor.png");
        }

        if (texture == null) return;

        this.getParentModel().copyPropertiesTo(this.armorModel);
        this.armorModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        this.armorModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));

        this.armorModel.renderToBuffer(
                poseStack,
                vertexconsumer,
                packedLight,
                OverlayTexture.NO_OVERLAY
        );
    }

}
