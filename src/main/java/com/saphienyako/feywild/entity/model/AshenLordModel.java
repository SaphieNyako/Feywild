package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.entity.MabEntity;
import com.saphienyako.feywild.entity.animations.MabAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class AshenLordModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart ashen_lord;
    private final ModelPart body;
    private final ModelPart upperbody;
    private final ModelPart branch1;
    private final ModelPart lantern;
    private final ModelPart branch2;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart beard;
    private final ModelPart left_arm;
    private final ModelPart left_arm2;
    private final ModelPart left_finger1;
    private final ModelPart left_finger2;
    private final ModelPart left_finger3;
    private final ModelPart left_lantern;
    private final ModelPart right_arm;
    private final ModelPart right_arm2;
    private final ModelPart right_finger1;
    private final ModelPart right_finger2;
    private final ModelPart right_finger3;
    private final ModelPart staff;
    private final ModelPart ornament;
    private final ModelPart right_lantern;
    private final ModelPart left_leg;
    private final ModelPart left_leg2;
    private final ModelPart left_finger_f1;
    private final ModelPart left_finger_f2;
    private final ModelPart left_finger_f3;
    private final ModelPart right_leg;
    private final ModelPart right_leg2;
    private final ModelPart right_finger_f1;
    private final ModelPart right_finger_f2;
    private final ModelPart right_finger_f3;

    public AshenLordModel(ModelPart root) {
        this.ashen_lord = root.getChild("ashen_lord");
        this.body = this.ashen_lord.getChild("body");
        this.upperbody = this.body.getChild("upperbody");
        this.branch1 = this.upperbody.getChild("branch1");
        this.lantern = this.branch1.getChild("lantern");
        this.branch2 = this.upperbody.getChild("branch2");
        this.head = this.ashen_lord.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.beard = this.jaw.getChild("beard");
        this.left_arm = this.ashen_lord.getChild("left_arm");
        this.left_arm2 = this.left_arm.getChild("left_arm2");
        this.left_finger1 = this.left_arm2.getChild("left_finger1");
        this.left_finger2 = this.left_arm2.getChild("left_finger2");
        this.left_finger3 = this.left_arm2.getChild("left_finger3");
        this.left_lantern = this.left_arm.getChild("left_lantern");
        this.right_arm = this.ashen_lord.getChild("right_arm");
        this.right_arm2 = this.right_arm.getChild("right_arm2");
        this.right_finger1 = this.right_arm2.getChild("right_finger1");
        this.right_finger2 = this.right_arm2.getChild("right_finger2");
        this.right_finger3 = this.right_arm2.getChild("right_finger3");
        this.staff = this.right_arm2.getChild("staff");
        this.ornament = this.staff.getChild("ornament");
        this.right_lantern = this.right_arm.getChild("right_lantern");
        this.left_leg = this.ashen_lord.getChild("left_leg");
        this.left_leg2 = this.left_leg.getChild("left_leg2");
        this.left_finger_f1 = this.left_leg2.getChild("left_finger_f1");
        this.left_finger_f2 = this.left_leg2.getChild("left_finger_f2");
        this.left_finger_f3 = this.left_leg2.getChild("left_finger_f3");
        this.right_leg = this.ashen_lord.getChild("right_leg");
        this.right_leg2 = this.right_leg.getChild("right_leg2");
        this.right_finger_f1 = this.right_leg2.getChild("right_finger_f1");
        this.right_finger_f2 = this.right_leg2.getChild("right_finger_f2");
        this.right_finger_f3 = this.right_leg2.getChild("right_finger_f3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ashen_lord = partdefinition.addOrReplaceChild("ashen_lord", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition body = ashen_lord.addOrReplaceChild("body", CubeListBuilder.create().texOffs(92, 0).addBox(-9.0F, -5.0F, -7.0F, 18.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(36, 122).addBox(-7.0F, -14.0F, -5.0F, 14.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upperbody = body.addOrReplaceChild("upperbody", CubeListBuilder.create().texOffs(0, 37).addBox(-16.0F, -19.0F, -9.0F, 32.0F, 19.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-18.0F, -20.0F, -10.0F, 36.0F, 17.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));

        PartDefinition branch1 = upperbody.addOrReplaceChild("branch1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -15.0F, -2.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(142, 143).addBox(2.5F, -15.0F, -2.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(142, 9).addBox(9.5F, -23.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(156, 13).addBox(2.5F, -20.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(112, 30).addBox(2.5F, -24.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(112, 26).addBox(-0.5F, -24.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(112, 19).addBox(15.5F, -28.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(156, 62).addBox(17.5F, -28.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(6.5F, -29.0F, -1.5F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, -19.0F, 0.0F, -0.2618F, -0.829F, 0.3491F));

        PartDefinition lantern = branch1.addOrReplaceChild("lantern", CubeListBuilder.create().texOffs(16, 38).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(12, 37).addBox(0.0F, 0.0F, -0.5F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(142, 121).addBox(-2.5F, 9.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(156, 66).addBox(-1.5F, 8.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -21.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition branch2 = upperbody.addOrReplaceChild("branch2", CubeListBuilder.create().texOffs(82, 37).addBox(-4.5F, -8.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 74).addBox(-1.5F, -8.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.5F, -19.0F, 0.5F));

        PartDefinition head = ashen_lord.addOrReplaceChild("head", CubeListBuilder.create().texOffs(94, 62).addBox(-6.0F, -7.0F, -12.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(124, 48).addBox(-6.0F, -9.0F, -12.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(141, 115).addBox(-7.0F, -4.0F, -14.0F, 14.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(92, 0).addBox(-2.0F, -1.0F, -14.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, -9.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(82, 37).addBox(-7.0F, -3.0F, -13.0F, 14.0F, 5.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

        PartDefinition beard = jaw.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(0, 116).addBox(-9.0F, -1.0F, -13.1F, 18.0F, 33.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(123, 7).addBox(-7.1F, -2.0F, -13.0F, 0.0F, 29.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(118, 113).addBox(7.1F, -2.0F, -13.0F, 0.0F, 29.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = ashen_lord.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(84, 125).addBox(0.0F, -2.0F, -4.0F, 9.0F, 24.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(130, 62).addBox(0.0F, -5.0F, -4.0F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(147, 19).addBox(9.0F, 2.0F, -2.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(64, 141).addBox(12.0F, -14.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(117, 55).addBox(5.0F, -12.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 126).addBox(5.0F, -16.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(82, 43).addBox(9.0F, 12.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(142, 82).addBox(16.0F, -9.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(110, 125).addBox(22.0F, -13.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(141, 102).addBox(2.0F, -18.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(130, 86).addBox(17.0F, -16.0F, -5.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(147, 41).addBox(13.0F, -4.0F, -4.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(147, 34).addBox(-2.0F, 3.0F, -7.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(12, 149).addBox(10.0F, 11.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(147, 27).addBox(4.0F, 6.0F, 2.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(155, 132).addBox(10.0F, -9.0F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(57, 141).addBox(11.0F, -7.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(155, 132).addBox(14.0F, -5.0F, 1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(57, 141).addBox(15.0F, -3.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(130, 65).addBox(15.0F, -6.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 122).addBox(15.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(80, 157).addBox(14.0F, 2.0F, -4.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(27, 157).addBox(10.0F, 0.0F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(86, 119).addBox(11.0F, -2.0F, 1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 155).addBox(-2.0F, 16.0F, -6.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(130, 92).addBox(1.0F, 14.0F, -6.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(89, 114).addBox(4.0F, 16.0F, -5.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(94, 86).addBox(1.0F, 18.0F, -5.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(89, 114).addBox(5.0F, 13.0F, 3.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(155, 132).addBox(-1.0F, 13.0F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(94, 86).addBox(2.0F, 15.0F, 4.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(130, 92).addBox(2.0F, 11.0F, 3.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -29.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition left_arm2 = left_arm.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(0, 74).addBox(-6.5F, 0.0F, -6.0F, 13.0F, 30.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(50, 74).addBox(-7.5F, -1.0F, -7.0F, 15.0F, 15.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(108, 86).addBox(2.5F, 21.0F, -8.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(100, 67).addBox(3.5F, 23.0F, -7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(100, 67).addBox(-1.5F, 19.0F, -7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(100, 65).addBox(1.5F, 14.0F, -7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(150, 152).addBox(3.5F, 19.0F, -7.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(125, 86).addBox(-0.5F, 16.0F, -7.5F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(100, 65).addBox(0.5F, 17.0F, 6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(100, 65).addBox(1.5F, 22.0F, 6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(108, 86).addBox(1.5F, 20.0F, 4.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(150, 152).addBox(3.5F, 24.0F, 3.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(100, 67).addBox(-3.5F, 23.0F, 6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(125, 86).addBox(-5.5F, 19.0F, 5.5F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 22.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition left_finger1 = left_arm2.addOrReplaceChild("left_finger1", CubeListBuilder.create().texOffs(38, 74).addBox(-8.0F, -2.5F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 28.5F, -5.5F));

        PartDefinition left_finger2 = left_arm2.addOrReplaceChild("left_finger2", CubeListBuilder.create().texOffs(142, 73).addBox(-10.0F, 0.0F, -2.5F, 10.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 30.0F, -3.0F));

        PartDefinition left_finger3 = left_arm2.addOrReplaceChild("left_finger3", CubeListBuilder.create().texOffs(142, 0).addBox(-10.0F, 0.0F, -2.5F, 10.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 30.0F, 3.0F));

        PartDefinition left_lantern = left_arm.addOrReplaceChild("left_lantern", CubeListBuilder.create().texOffs(16, 37).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(12, 36).addBox(0.0F, 0.0F, -0.5F, 0.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(156, 66).addBox(-1.5F, 9.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(142, 121).addBox(-2.5F, 10.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition right_arm = ashen_lord.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(84, 125).mirror().addBox(-9.0F, -2.0F, -4.0F, 9.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(130, 62).mirror().addBox(-9.0F, -5.0F, -4.0F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(147, 19).mirror().addBox(-16.0F, 2.0F, -2.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(64, 141).mirror().addBox(-16.0F, -14.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(117, 55).mirror().addBox(-12.0F, -12.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(36, 126).mirror().addBox(-7.0F, -16.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(82, 43).mirror().addBox(-12.0F, 12.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(142, 82).mirror().addBox(-24.0F, -9.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(110, 125).mirror().addBox(-24.0F, -13.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(141, 102).mirror().addBox(-10.0F, -18.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(130, 86).mirror().addBox(-27.0F, -16.0F, -5.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(147, 41).mirror().addBox(-18.0F, -4.0F, -4.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(147, 34).mirror().addBox(-4.0F, 3.0F, -7.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(12, 149).mirror().addBox(-14.0F, 11.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(147, 27).mirror().addBox(-10.0F, 6.0F, 2.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(155, 132).mirror().addBox(-14.0F, -9.0F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(57, 141).mirror().addBox(-13.0F, -7.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(155, 132).mirror().addBox(-18.0F, -5.0F, 1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(57, 141).mirror().addBox(-17.0F, -3.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(130, 65).mirror().addBox(-17.0F, -6.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(36, 122).mirror().addBox(-17.0F, 0.0F, -3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(80, 157).mirror().addBox(-17.0F, 2.0F, -4.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(27, 157).mirror().addBox(-13.0F, 0.0F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(86, 119).mirror().addBox(-13.0F, -2.0F, 1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(14, 155).mirror().addBox(-2.0F, 16.0F, -6.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(130, 92).mirror().addBox(-4.0F, 14.0F, -6.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(89, 114).mirror().addBox(-6.0F, 16.0F, -5.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(94, 86).mirror().addBox(-4.0F, 18.0F, -5.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(89, 114).mirror().addBox(-7.0F, 13.0F, 3.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(155, 132).mirror().addBox(-3.0F, 13.0F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(94, 86).mirror().addBox(-5.0F, 15.0F, 4.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(130, 92).mirror().addBox(-5.0F, 11.0F, 3.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-16.0F, -29.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition right_arm2 = right_arm.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(0, 74).mirror().addBox(-6.5F, 0.0F, -6.0F, 13.0F, 30.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(50, 74).mirror().addBox(-7.5F, -1.0F, -7.0F, 15.0F, 15.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(108, 86).mirror().addBox(-8.5F, 21.0F, -8.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(100, 67).mirror().addBox(-5.5F, 23.0F, -7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(100, 67).mirror().addBox(-0.5F, 19.0F, -7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(100, 65).mirror().addBox(-3.5F, 14.0F, -7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(150, 152).mirror().addBox(-7.5F, 19.0F, -7.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(125, 86).mirror().addBox(-4.5F, 16.0F, -7.5F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(100, 65).mirror().addBox(-2.5F, 17.0F, 6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(100, 65).mirror().addBox(-3.5F, 22.0F, 6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(108, 86).mirror().addBox(-7.5F, 20.0F, 4.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(150, 152).mirror().addBox(-7.5F, 24.0F, 3.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(100, 67).mirror().addBox(1.5F, 23.0F, 6.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(125, 86).mirror().addBox(0.5F, 19.0F, 5.5F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 22.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition right_finger1 = right_arm2.addOrReplaceChild("right_finger1", CubeListBuilder.create().texOffs(38, 74).mirror().addBox(0.0F, -2.5F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 28.5F, -5.5F, -0.2268F, -0.2595F, 0.3009F));

        PartDefinition right_finger2 = right_arm2.addOrReplaceChild("right_finger2", CubeListBuilder.create().texOffs(142, 73).mirror().addBox(0.0F, 0.0F, -2.5F, 10.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.5F, 30.0F, -3.0F));

        PartDefinition right_finger3 = right_arm2.addOrReplaceChild("right_finger3", CubeListBuilder.create().texOffs(142, 0).mirror().addBox(0.0F, 0.0F, -2.5F, 10.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.5F, 30.0F, 3.0F));

        PartDefinition staff = right_arm2.addOrReplaceChild("staff", CubeListBuilder.create().texOffs(222, 54).addBox(-4.0F, 13.0F, -3.0833F, 8.0F, 12.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(196, 32).addBox(-2.5F, -8.0F, -2.0833F, 5.0F, 21.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(216, 32).addBox(-5.0F, -20.0F, -5.0833F, 10.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(202, 7).addBox(-2.0F, -17.0F, -10.0833F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(208, 2).addBox(-2.0F, -20.0F, -10.0833F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(204, 0).addBox(-5.0F, -36.0F, -5.0833F, 10.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 30.5F, 0.0833F, 1.5708F, 0.0F, 0.0F));

        PartDefinition ornament = staff.addOrReplaceChild("ornament", CubeListBuilder.create().texOffs(204, -1).addBox(0.0F, 0.0F, -0.5F, 0.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(204, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(244, 0).addBox(-1.5F, 5.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, -7.5833F));

        PartDefinition right_lantern = right_arm.addOrReplaceChild("right_lantern", CubeListBuilder.create().texOffs(16, 37).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(12, 36).mirror().addBox(0.0F, 0.0F, -0.5F, 0.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(156, 66).mirror().addBox(-1.5F, 9.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(142, 121).mirror().addBox(-2.5F, 10.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-20.5F, -7.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition left_leg = ashen_lord.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(36, 141).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, -4.0F, 0.0F));

        PartDefinition left_leg2 = left_leg.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(97, 92).addBox(-5.0F, 0.0F, -5.0F, 11.0F, 22.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(37, 103).addBox(-6.0F, -1.0F, -6.0F, 13.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 12.0F, -0.5F));

        PartDefinition left_finger_f1 = left_leg2.addOrReplaceChild("left_finger_f1", CubeListBuilder.create().texOffs(0, 149).addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 20.5F, -4.0F, 0.0F, 0.1309F, 0.0F));

        PartDefinition left_finger_f2 = left_leg2.addOrReplaceChild("left_finger_f2", CubeListBuilder.create().texOffs(74, 122).addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 20.5F, -4.0F, 0.0F, -0.1309F, 0.0F));

        PartDefinition left_finger_f3 = left_leg2.addOrReplaceChild("left_finger_f3", CubeListBuilder.create().texOffs(142, 132).addBox(-1.5F, -2.5F, -7.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 20.5F, -5.0F));

        PartDefinition right_leg = ashen_lord.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(36, 141).mirror().addBox(-3.5F, 0.0F, -3.5F, 7.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.5F, -4.0F, 0.0F));

        PartDefinition right_leg2 = right_leg.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(97, 92).mirror().addBox(-6.0F, 0.0F, -5.0F, 11.0F, 22.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(37, 103).mirror().addBox(-7.0F, -1.0F, -6.0F, 13.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 12.0F, -0.5F));

        PartDefinition right_finger_f1 = right_leg2.addOrReplaceChild("right_finger_f1", CubeListBuilder.create().texOffs(0, 149).mirror().addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 20.5F, -4.0F, 0.0F, -0.1309F, 0.0F));

        PartDefinition right_finger_f2 = right_leg2.addOrReplaceChild("right_finger_f2", CubeListBuilder.create().texOffs(74, 122).mirror().addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 20.5F, -4.0F, 0.0F, 0.1309F, 0.0F));

        PartDefinition right_finger_f3 = right_leg2.addOrReplaceChild("right_finger_f3", CubeListBuilder.create().texOffs(142, 132).mirror().addBox(-1.5F, -2.5F, -7.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 20.5F, -5.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

      //TODO ANIMATIONS
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        ashen_lord.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return ashen_lord;
    }
}
