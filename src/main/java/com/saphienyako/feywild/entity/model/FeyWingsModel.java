package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class FeyWingsModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart fey_wings;
    private final ModelPart wings;
    private final ModelPart left;
    private final ModelPart leftbottom;
    private final ModelPart right;
    private final ModelPart rightbottom;

    public FeyWingsModel(ModelPart root) {
        this.fey_wings = root.getChild("fey_wings");
        this.wings = this.fey_wings.getChild("wings");
        this.left = this.wings.getChild("left");
        this.leftbottom = this.left.getChild("leftbottom");
        this.right = this.wings.getChild("right");
        this.rightbottom = this.right.getChild("rightbottom");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition fey_wings = partdefinition.addOrReplaceChild("fey_wings", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 1.25F, 0.0F, 0.0F, 0.0F));

        PartDefinition wings = fey_wings.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 2.1F, -0.0F, 0.0F, 0.0F));

        PartDefinition left = wings.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -14.0F, 0.0F, 20.0F, 14.0F, 0.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftbottom = left.addOrReplaceChild("leftbottom", CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, 0.0F, 0.0F, 20.0F, 14.0F, 0.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition right = wings.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-20.0F, -14.0F, 0.0F, 20.0F, 14.0F, 0.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightbottom = right.addOrReplaceChild("rightbottom", CubeListBuilder.create().texOffs(0, 30).mirror().addBox(-20.0F, 0.0F, 0.0F, 20.0F, 14.0F, 0.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float time = ageInTicks * 0.8F;

        float flap = Mth.sin(time) * 0.25F;
        float secondary = Mth.sin(time * 1.5F) * 0.12F;

        float wingSpread = 0.35F;

        // LEFT wing
        left.yRot = -(wingSpread + flap);
        left.xRot = 0.20F;

        leftbottom.yRot = -(wingSpread + flap + secondary);
        leftbottom.xRot = 0.20F;

        // RIGHT wing
        right.yRot = wingSpread + flap;
        right.xRot = 0.20F;

        rightbottom.yRot = wingSpread + flap + secondary;
        rightbottom.xRot = 0.20F;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        fey_wings.render(poseStack,buffer,packedLight,packedOverlay,red,green,blue,alpha);
    }

    @Override
    public @NotNull ModelPart root() {
        return fey_wings;
    }
}
