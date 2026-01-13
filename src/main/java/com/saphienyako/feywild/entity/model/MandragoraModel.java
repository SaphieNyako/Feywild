package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.entity.MandragoraEntity;
import com.saphienyako.feywild.entity.animations.MandragoraAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class MandragoraModel<T extends Entity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
  //  public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "mandragora"), "main");
    private final ModelPart mandragora;
    private final ModelPart body;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart torso;
    private final ModelPart skirt;
    private final ModelPart head;
    private final ModelPart flower;
    private final ModelPart hair;
    private final ModelPart front_hair;
    private final ModelPart front_hair2;
    private final ModelPart right_hair;
    private final ModelPart right_hair2;
    private final ModelPart left_hair;
    private final ModelPart left_hair2;
    private final ModelPart left_arm;
    private final ModelPart right_arm;

    public MandragoraModel(ModelPart root) {
        this.mandragora = root.getChild("root");
        this.body = this.mandragora.getChild("body");
        this.left_leg = this.body.getChild("left_leg");
        this.right_leg = this.body.getChild("right_leg");
        this.torso = this.body.getChild("torso");
        this.skirt = this.torso.getChild("skirt");
        this.head = this.torso.getChild("head");
        this.flower = this.head.getChild("flower");
        this.hair = this.head.getChild("hair");
        this.front_hair = this.hair.getChild("front_hair");
        this.front_hair2 = this.front_hair.getChild("front_hair2");
        this.right_hair = this.hair.getChild("right_hair");
        this.right_hair2 = this.right_hair.getChild("right_hair2");
        this.left_hair = this.hair.getChild("left_hair");
        this.left_hair2 = this.left_hair.getChild("left_hair2");
        this.left_arm = this.torso.getChild("left_arm");
        this.right_arm = this.torso.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -4.0F, 0.0F));

        PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, -4.0F, 0.0F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(24, 22).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition skirt = torso.addOrReplaceChild("skirt", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = skirt.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r2 = skirt.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r3 = skirt.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 2).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.4363F));

        PartDefinition cube_r4 = skirt.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 2).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 0.0F, 0.0F, 0.0F, 1.5708F, -0.4363F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 22).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition flower = head.addOrReplaceChild("flower", CubeListBuilder.create().texOffs(0, 7).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(4.0F, -8.0F, -4.0F, -0.8727F, 0.0873F, 0.6981F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition front_hair = hair.addOrReplaceChild("front_hair", CubeListBuilder.create().texOffs(10, 2).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -2.0F, 0.5787F, 0.4804F, 0.067F));

        PartDefinition cube_r5 = front_hair.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(10, 2).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition front_hair2 = front_hair.addOrReplaceChild("front_hair2", CubeListBuilder.create().texOffs(-13, 39).addBox(-5.0F, 0.0F, -9.0F, 10.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition right_hair = hair.addOrReplaceChild("right_hair", CubeListBuilder.create().texOffs(10, 2).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 3.5F, -2.5188F, 0.3903F, -3.1274F));

        PartDefinition cube_r6 = right_hair.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(10, 2).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition right_hair2 = right_hair.addOrReplaceChild("right_hair2", CubeListBuilder.create().texOffs(-13, 39).mirror().addBox(-5.0F, 0.0F, -9.0F, 10.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition left_hair = hair.addOrReplaceChild("left_hair", CubeListBuilder.create().texOffs(10, 2).mirror().addBox(-0.5F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 0.0F, 1.0F, -1.9318F, -1.3216F, 2.677F));

        PartDefinition cube_r7 = left_hair.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(10, 2).mirror().addBox(-0.5F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition left_hair2 = left_hair.addOrReplaceChild("left_hair2", CubeListBuilder.create().texOffs(-13, 39).addBox(-5.0F, 0.0F, -9.0F, 10.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 30).addBox(0.0F, -1.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -2.5F, 0.0F));

        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 30).mirror().addBox(-2.0F, -1.5F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, -2.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animate(((MandragoraEntity)entity).IDLE_ANIMATION, MandragoraAnimations.IDLE, ageInTicks, 1.0f);
        this.animate(((MandragoraEntity)entity).SING_ANIMATION, MandragoraAnimations.SING, ageInTicks, 1.0f);
        this.animate(((MandragoraEntity)entity).POSE_ANIMATION, MandragoraAnimations.POSE, ageInTicks, 1.0f);
        this.animate(((MandragoraEntity)entity).WALK_ANIMATION, MandragoraAnimations.WALK, ageInTicks, 1.0f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        mandragora.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return mandragora;
    }

}