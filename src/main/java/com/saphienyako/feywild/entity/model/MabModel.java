package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.entity.MabEntity;
import com.saphienyako.feywild.entity.TitaniaEntity;
import com.saphienyako.feywild.entity.animations.MabAnimations;
import com.saphienyako.feywild.entity.animations.TitaniaAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class MabModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart mab;
    private final ModelPart head;
    private final ModelPart crown;
    private final ModelPart righthear;
    private final ModelPart lefthear;
    private final ModelPart hair;
    private final ModelPart leftfronthair;
    private final ModelPart rightfronthair;
    private final ModelPart backhair;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart sword;
    private final ModelPart left_arm;
    private final ModelPart bone3;
    private final ModelPart leftwing;
    private final ModelPart bottom_left_wing;
    private final ModelPart upper_left_wing;
    private final ModelPart rightwing;
    private final ModelPart bottom_right_wing;
    private final ModelPart upper_right_wing;
    private final ModelPart skirt;
    private final ModelPart rightskirt;
    private final ModelPart leftskirt;
    private final ModelPart right_leg;
    private final ModelPart left_leg;

    public MabModel(ModelPart root) {
        this.mab = root.getChild("mab");
        this.head = this.mab.getChild("head");
        this.crown = this.head.getChild("crown");
        this.righthear = this.head.getChild("righthear");
        this.lefthear = this.head.getChild("lefthear");
        this.hair = this.head.getChild("hair");
        this.leftfronthair = this.hair.getChild("leftfronthair");
        this.rightfronthair = this.hair.getChild("rightfronthair");
        this.backhair = this.hair.getChild("backhair");
        this.body = this.mab.getChild("body");
        this.right_arm = this.mab.getChild("right_arm");
        this.sword = this.right_arm.getChild("sword");
        this.left_arm = this.mab.getChild("left_arm");
        this.bone3 = this.left_arm.getChild("bone3");
        this.leftwing = this.mab.getChild("leftwing");
        this.bottom_left_wing = this.leftwing.getChild("bottom_left_wing");
        this.upper_left_wing = this.leftwing.getChild("upper_left_wing");
        this.rightwing = this.mab.getChild("rightwing");
        this.bottom_right_wing = this.rightwing.getChild("bottom_right_wing");
        this.upper_right_wing = this.rightwing.getChild("upper_right_wing");
        this.skirt = this.mab.getChild("skirt");
        this.rightskirt = this.skirt.getChild("rightskirt");
        this.leftskirt = this.skirt.getChild("leftskirt");
        this.right_leg = this.mab.getChild("right_leg");
        this.left_leg = this.mab.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition mab = partdefinition.addOrReplaceChild("mab", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition head = mab.addOrReplaceChild("head", CubeListBuilder.create().texOffs(56, 20).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(21, 0).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

        PartDefinition crown = head.addOrReplaceChild("crown", CubeListBuilder.create().texOffs(129, 3).addBox(-4.5F, -20.0F, -6.0F, 9.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition cube_r1 = crown.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 30).addBox(0.0F, -3.5F, 0.0F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -12.5F, -6.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition cube_r2 = crown.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 30).addBox(0.0F, -3.5F, 0.0F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -12.5F, -6.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition righthear = head.addOrReplaceChild("righthear", CubeListBuilder.create().texOffs(56, 0).addBox(0.0F, -3.25F, 0.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -3.75F, -2.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition lefthear = head.addOrReplaceChild("lefthear", CubeListBuilder.create().texOffs(56, 0).addBox(0.0F, -3.25F, 0.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -3.75F, -2.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(56, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftfronthair = hair.addOrReplaceChild("leftfronthair", CubeListBuilder.create().texOffs(104, 23).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.1667F, -5.2333F, -0.0873F, 0.0F, 0.6981F));

        PartDefinition rightfronthair = hair.addOrReplaceChild("rightfronthair", CubeListBuilder.create().texOffs(104, 23).mirror().addBox(0.0F, 0.0F, 0.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -10.1667F, -5.2333F, -0.0873F, 0.0F, -0.6981F));

        PartDefinition backhair = hair.addOrReplaceChild("backhair", CubeListBuilder.create().texOffs(88, 72).addBox(-6.0F, -1.25F, -0.5F, 12.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(-6.0F, 12.75F, 0.0F, 12.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.75F, 5.5F, 0.1309F, 0.0F, 0.0F));

        PartDefinition body = mab.addOrReplaceChild("body", CubeListBuilder.create().texOffs(88, 56).addBox(-4.5F, -14.6667F, -2.1667F, 9.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(86, 0).mirror().addBox(0.5F, -13.6667F, -3.1667F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(86, 0).addBox(-4.5F, -13.6667F, -3.1667F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(116, 90).addBox(-3.5F, -5.6667F, -1.1667F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.3333F, -1.3333F));

        PartDefinition right_arm = mab.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(113, 23).addBox(-0.5F, -2.0F, -2.0F, 4.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -12.0F, 0.0F, 0.0F, 0.0F, -0.48F));

        PartDefinition sword = right_arm.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(237, 0).addBox(-1.0F, -2.4369F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(246, 0).addBox(-1.5F, 1.5631F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(240, 5).addBox(-3.0F, -3.4369F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(229, 4).addBox(-1.5F, -5.4369F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(228, 10).addBox(-2.0F, -32.4369F, -0.5F, 4.0F, 29.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 13.9369F, -0.5F, 1.5708F, -0.9163F, 1.5708F));

        PartDefinition cube_r3 = sword.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(238, 13).addBox(0.0F, 0.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -34.4369F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition left_arm = mab.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(113, 23).mirror().addBox(-3.5F, -1.0F, -2.0F, 4.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition bone3 = left_arm.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.5753F, 7.4975F, 0.0F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition leftwing = mab.addOrReplaceChild("leftwing", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.25F, -11.0F, 8.5F, 0.2758F, -1.1541F, -0.923F));

        PartDefinition bottom_left_wing = leftwing.addOrReplaceChild("bottom_left_wing", CubeListBuilder.create().texOffs(0, 67).addBox(0.0F, 0.0F, 0.0F, 0.0F, 34.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition upper_left_wing = leftwing.addOrReplaceChild("upper_left_wing", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -24.0F, 0.0F, 0.0F, 24.0F, 44.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.9548F, -0.1675F, 0.0406F));

        PartDefinition rightwing = mab.addOrReplaceChild("rightwing", CubeListBuilder.create(), PartPose.offsetAndRotation(2.25F, -11.0F, 8.5F, 0.2758F, 1.1541F, 0.923F));

        PartDefinition bottom_right_wing = rightwing.addOrReplaceChild("bottom_right_wing", CubeListBuilder.create().texOffs(0, 67).mirror().addBox(0.0F, 0.0F, 0.0F, 0.0F, 34.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition upper_right_wing = rightwing.addOrReplaceChild("upper_right_wing", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -24.0F, 0.0F, 0.0F, 24.0F, 44.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.9548F, 0.1675F, -0.0406F));

        PartDefinition skirt = mab.addOrReplaceChild("skirt", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightskirt = skirt.addOrReplaceChild("rightskirt", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -1.0F, -3.5F, 7.0F, 30.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition leftskirt = skirt.addOrReplaceChild("leftskirt", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -1.0F, -3.5F, 7.0F, 30.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition right_leg = mab.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(112, 0).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 19.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.5F, 0.0F));

        PartDefinition left_leg = mab.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(112, 0).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 19.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animate(((MabEntity)entity).FLYING_IDLE_ANIMATION, MabAnimations.FLYING_IDLE, ageInTicks, 1.0f);
        this.animate(((MabEntity)entity).FLYING_ANIMATION, MabAnimations.FLYING, ageInTicks, 1.0f);
        this.animate(((MabEntity)entity).CHANNEL_ANIMATION, MabAnimations.CHANNEL, ageInTicks, 1.0f);
        this.animate(((MabEntity)entity).INTIMIDATION_ANIMATION, MabAnimations.INTIMIDATION, ageInTicks, 1.0f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        mab.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return mab;
    }
}
