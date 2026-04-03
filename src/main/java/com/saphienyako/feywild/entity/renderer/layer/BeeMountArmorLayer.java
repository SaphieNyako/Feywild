package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.entity.model.BeeMountModel;
import com.saphienyako.feywild.entity.model.BellsnickelModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeeMountArmorLayer extends RenderLayer<BeeMountEntity, BeeMountModel<BeeMountEntity>> {

    private final BeeMountModel<BeeMountEntity> armorModel;

    public BeeMountArmorLayer(RenderLayerParent<BeeMountEntity, BeeMountModel<BeeMountEntity>> parent,
                              EntityModelSet modelSet) {
        super(parent);
        this.armorModel = new BeeMountModel<>(modelSet.bakeLayer(ModModelLayers.BEE_MOUNT_LAYER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, BeeMountEntity entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        ResourceLocation texture = null;

        if (entity.getEntityData().get(BeeMountEntity.MOUNT_HAS_GOLD_ARMOR)) {
            texture = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_gold_armor.png");
        } else if (entity.getEntityData().get(BeeMountEntity.MOUNT_HAS_DIAMOND_ARMOR)) {
            texture = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_diamond_armor.png");
        } //TODO 1.21.11 has Netherrite Armor

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
