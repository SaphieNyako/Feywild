package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.entity.AutumnPixieEntity;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.animations.AutumnPixieAnimations;
import com.saphienyako.feywild.entity.animations.BeeKnightAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class BeeKnightModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart bee_knight;
    private final ModelPart body;
    private final ModelPart torso;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart head;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart left_hair;
    private final ModelPart right_hair;
    private final ModelPart hair;
    private final ModelPart right_wing;
    private final ModelPart right_wing_armor;
    private final ModelPart left_wing;
    private final ModelPart left_wing_armor;
    private final ModelPart skirt;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public BeeKnightModel(ModelPart root) {
        this.bee_knight = root.getChild("bee_knight");
        this.body = this.bee_knight.getChild("body");
        this.torso = this.body.getChild("torso");
        this.right_arm = this.torso.getChild("right_arm");
        this.left_arm = this.torso.getChild("left_arm");
        this.head = this.torso.getChild("head");
        this.left_ear = this.head.getChild("left_ear");
        this.right_ear = this.head.getChild("right_ear");
        this.left_hair = this.head.getChild("left_hair");
        this.right_hair = this.head.getChild("right_hair");
        this.hair = this.head.getChild("hair");
        this.right_wing = this.torso.getChild("right_wing");
        this.right_wing_armor = this.right_wing.getChild("right_wing_armor");
        this.left_wing = this.torso.getChild("left_wing");
        this.left_wing_armor = this.left_wing.getChild("left_wing_armor");
        this.skirt = this.torso.getChild("skirt");
        this.left_leg = this.body.getChild("left_leg");
        this.right_leg = this.body.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bee_knight = partdefinition.addOrReplaceChild("bee_knight", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition body = bee_knight.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(18, 14).addBox(-2.5F, -6.0F, -2.0F, 5.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 27).addBox(-2.5F, -6.0F, -2.0F, 5.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 24).mirror().addBox(-2.5F, 1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(23, 0).mirror().addBox(-3.0F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(23, 55).mirror().addBox(-3.0F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(-2.5F, -5.0F, 0.0F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 24).addBox(0.5F, 1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(23, 0).addBox(0.0F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(23, 55).addBox(0.0F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.2F)), PartPose.offset(2.5F, -5.0F, 0.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -3.5F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(5, 6).addBox(4.0F, -3.0F, -3.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(5, 6).addBox(-5.0F, -3.0F, -3.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(31, 0).addBox(-4.0F, -7.0F, -3.5F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.1F))
                .texOffs(0, 41).addBox(-4.0F, -7.0F, -3.5F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(23, 2).mirror().addBox(0.0F, -1.5F, -3.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.1F, -5.5F, -3.5F, 0.0F, 0.1309F, 0.0F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(23, 2).addBox(0.0F, -1.5F, -3.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1F, -5.5F, -3.5F, 0.0F, -0.1309F, 0.0F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(56, -2).mirror().addBox(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -2.0F, -1.5F, 0.0F, 0.48F, 0.0F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(56, -2).mirror().addBox(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -2.0F, -1.5F, 0.0F, -0.48F, 0.0F));

        PartDefinition left_hair = head.addOrReplaceChild("left_hair", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -6.0F, -3.6F, -0.1229F, 0.004F, -0.3084F));

        PartDefinition right_hair = head.addOrReplaceChild("right_hair", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -6.0F, -3.6F, -0.1229F, -0.004F, 0.3084F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(52, 26).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.5F, 3.5F, 0.1309F, 0.0F, 0.0F));

        PartDefinition right_wing = torso.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(38, 24).mirror().addBox(0.0388F, -12.4837F, 0.0153F, 0.0F, 27.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.3F, -3.5F, 2.6F));

        PartDefinition right_wing_armor = right_wing.addOrReplaceChild("right_wing_armor", CubeListBuilder.create().texOffs(23, 35).mirror().addBox(-0.5F, -3.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 48).mirror().addBox(-0.5F, -3.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0388F, -5.4837F, 3.0153F));

        PartDefinition armor_r1 = right_wing_armor.addOrReplaceChild("armor_r1", CubeListBuilder.create().texOffs(30, 48).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, -2.5F, 1.5708F, 0.0F, -3.1416F));

        PartDefinition left_wing = torso.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(38, 24).addBox(-0.0388F, -12.4837F, 0.0153F, 0.0F, 27.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(3.3F, -3.5F, 2.6F));

        PartDefinition left_wing_armor = left_wing.addOrReplaceChild("left_wing_armor", CubeListBuilder.create().texOffs(23, 35).addBox(-0.5F, -3.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(30, 48).addBox(-0.5F, -3.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0388F, -5.4837F, 3.0153F));

        PartDefinition armor_r2 = left_wing_armor.addOrReplaceChild("armor_r2", CubeListBuilder.create().texOffs(30, 48).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -2.5F, 1.5708F, 0.0F, 3.1416F));

        PartDefinition skirt = torso.addOrReplaceChild("skirt", CubeListBuilder.create().texOffs(36, 14).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 6.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(0, 55).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(25, 24).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 6.0F, 0.0F));

        PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(25, 24).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 6.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        bee_knight.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @NotNull
    @Override
    public ModelPart root() {
        return bee_knight;
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animate(((BeeKnightEntity)entity).SIT_ANIMATION, BeeKnightAnimations.SIT, ageInTicks, 2f);
        this.animate(((BeeKnightEntity)entity).ATTACK_ANIMATION, BeeKnightAnimations.ATTACK_RIGHT, ageInTicks, 1.0f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    public ModelPart getRightArm() {
        return this.right_arm;
    }
}
