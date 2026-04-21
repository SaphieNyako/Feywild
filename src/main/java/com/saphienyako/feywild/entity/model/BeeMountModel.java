package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.entity.animations.BeeKnightAnimations;
import com.saphienyako.feywild.entity.animations.BeeMountAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class BeeMountModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart bee_mount;
    private final ModelPart body;
    private final ModelPart abdomen;
    private final ModelPart stinger;
    private final ModelPart left_wing;
    private final ModelPart left_wing_armor;
    private final ModelPart right_wing;
    private final ModelPart right_wing_armor;
    private final ModelPart left_leg2;
    private final ModelPart right_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg3;
    private final ModelPart mane;
    private final ModelPart saddle;
    private final ModelPart head;
    private final ModelPart left_antenna;
    private final ModelPart left_antenna2;
    private final ModelPart right_antenna;
    private final ModelPart right_antenna2;
    private final ModelPart left_leg1;
    private final ModelPart right_leg1;

    public BeeMountModel(ModelPart root) {
        this.bee_mount = root.getChild("bee_mount");
        this.body = this.bee_mount.getChild("body");
        this.abdomen = this.body.getChild("abdomen");
        this.stinger = this.abdomen.getChild("stinger");
        this.left_wing = this.abdomen.getChild("left_wing");
        this.left_wing_armor = this.left_wing.getChild("left_wing_armor");
        this.right_wing = this.abdomen.getChild("right_wing");
        this.right_wing_armor = this.right_wing.getChild("right_wing_armor");
        this.left_leg2 = this.abdomen.getChild("left_leg2");
        this.right_leg2 = this.abdomen.getChild("right_leg2");
        this.left_leg3 = this.abdomen.getChild("left_leg3");
        this.right_leg3 = this.abdomen.getChild("right_leg3");
        this.mane = this.body.getChild("mane");
        this.saddle = this.mane.getChild("saddle");
        this.head = this.mane.getChild("head");
        this.left_antenna = this.head.getChild("left_antenna");
        this.left_antenna2 = this.left_antenna.getChild("left_antenna2");
        this.right_antenna = this.head.getChild("right_antenna");
        this.right_antenna2 = this.right_antenna.getChild("right_antenna2");
        this.left_leg1 = this.mane.getChild("left_leg1");
        this.right_leg1 = this.mane.getChild("right_leg1");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bee_mount = partdefinition.addOrReplaceChild("bee_mount", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = bee_mount.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -5.75F, -1.5F));

        PartDefinition abdomen = body.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(0, 25).addBox(-5.5F, -5.5F, -0.5F, 11.0F, 11.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(40, 68).addBox(-5.5F, -5.5F, -0.5F, 11.0F, 5.0F, 12.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.25F, 2.5F));

        PartDefinition stinger = abdomen.addOrReplaceChild("stinger", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 11.5F));

        PartDefinition left_wing = abdomen.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(50, 2).addBox(0.0F, -12.0F, -1.0F, 0.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -5.5F, 3.5F));

        PartDefinition left_wing_armor = left_wing.addOrReplaceChild("left_wing_armor", CubeListBuilder.create().texOffs(0, 73).addBox(-0.5F, -3.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.02F))
                .texOffs(0, 73).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.02F))
                .texOffs(0, 75).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, -8.0F, 1.0F));

        PartDefinition right_wing = abdomen.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(50, 2).addBox(0.0F, -12.0F, -1.0F, 0.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, -5.5F, 3.5F));

        PartDefinition right_wing_armor = right_wing.addOrReplaceChild("right_wing_armor", CubeListBuilder.create().texOffs(0, 73).mirror().addBox(-0.5F, -3.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(0, 73).mirror().addBox(-0.5F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(0, 75).mirror().addBox(-0.5F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, -8.0F, 1.0F));

        PartDefinition left_leg2 = abdomen.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(34, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 74).addBox(-1.5F, 1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offset(3.5F, 5.5F, 0.0F));

        PartDefinition right_leg2 = abdomen.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(34, 25).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 74).mirror().addBox(-1.5F, 1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(-3.5F, 5.5F, 0.0F));

        PartDefinition left_leg3 = abdomen.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(34, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 74).addBox(-1.5F, 1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offset(3.25F, 5.5F, 7.0F));

        PartDefinition right_leg3 = abdomen.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(34, 25).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 74).mirror().addBox(-1.5F, 1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(-3.25F, 5.5F, 7.0F));

        PartDefinition mane = body.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.0F, -5.5F, 14.0F, 14.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 73).addBox(-7.0F, -7.0F, -5.5F, 14.0F, 5.0F, 12.0F, new CubeDeformation(0.2F))
                .texOffs(0, 48).addBox(-7.0F, -7.0F, 5.5F, 14.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.25F, -2.5F));

        PartDefinition saddle = mane.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(78, 0).addBox(-7.0F, -1.0F, -6.5F, 14.0F, 2.0F, 11.0F, new CubeDeformation(0.255F))
                .texOffs(70, 17).addBox(-5.0F, -3.0F, -6.5F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.257F))
                .texOffs(74, 13).addBox(-4.0F, -2.0F, 3.5F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.257F))
                .texOffs(94, 13).addBox(-7.0F, 1.0F, -2.5F, 14.0F, 12.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -6.0F, 2.0F));

        PartDefinition head = mane.addOrReplaceChild("head", CubeListBuilder.create().texOffs(39, 0).addBox(-4.0F, -4.0F, -2.5F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 65).addBox(-4.0F, -4.0F, -2.5F, 8.0F, 5.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 2.0F, -5.0F));

        PartDefinition left_antenna = head.addOrReplaceChild("left_antenna", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.5F, -3.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -2.5F, -2.5F));

        PartDefinition left_antenna2 = left_antenna.addOrReplaceChild("left_antenna2", CubeListBuilder.create().texOffs(0, -3).addBox(0.0F, -0.5F, -3.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.0F, -3.0F));

        PartDefinition right_antenna = head.addOrReplaceChild("right_antenna", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.5F, -3.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -2.5F, -2.5F));

        PartDefinition right_antenna2 = right_antenna.addOrReplaceChild("right_antenna2", CubeListBuilder.create().texOffs(0, -3).addBox(0.0F, -0.5F, -3.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.0F, -3.0F));

        PartDefinition left_leg1 = mane.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(34, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 74).addBox(-1.5F, 1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offset(2.5F, 6.0F, -2.0F));

        PartDefinition right_leg1 = mane.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(34, 25).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 74).mirror().addBox(-1.5F, 1.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(-2.5F, 6.0F, -2.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bee_mount.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @NotNull
    @Override
    public ModelPart root() {
        return bee_mount;
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animate(((BeeMountEntity)entity).FLY_ANIMATION, BeeMountAnimations.FLY, ageInTicks, 2f);
        this.animate(((BeeMountEntity)entity).FLY_IDLE_ANIMATION, BeeMountAnimations.FLY_IDLE, ageInTicks, 1.0f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }
}
