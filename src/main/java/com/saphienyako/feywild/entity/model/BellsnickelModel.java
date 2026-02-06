package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.entity.animations.BellsnickelAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class BellsnickelModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart bellsnickel;
    private final ModelPart body;
    private final ModelPart torso;
    private final ModelPart head;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart beard;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart book1;
    private final ModelPart book2;
    private final ModelPart book3;
    private final ModelPart backpack;
    private final ModelPart bedroll;
    private final ModelPart book4;
    private final ModelPart book5;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public BellsnickelModel(ModelPart root) {
        this.bellsnickel = root.getChild("root");
        this.body = this.bellsnickel.getChild("body");
        this.torso = this.body.getChild("torso");
        this.head = this.torso.getChild("head");
        this.left_ear = this.head.getChild("left_ear");
        this.right_ear = this.head.getChild("right_ear");
        this.beard = this.head.getChild("beard");
        this.left_arm = this.torso.getChild("left_arm");
        this.right_arm = this.torso.getChild("right_arm");
        this.book1 = this.torso.getChild("book1");
        this.book2 = this.book1.getChild("book2");
        this.book3 = this.book2.getChild("book3");
        this.backpack = this.torso.getChild("backpack");
        this.bedroll = this.backpack.getChild("bedroll");
        this.book4 = this.backpack.getChild("book4");
        this.book5 = this.backpack.getChild("book5");
        this.left_leg = this.body.getChild("left_leg");
        this.right_leg = this.body.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, 2.5F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -7.5F, -5.5F, 12.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-6.0F, -13.5F, -5.5F, 12.0F, 13.0F, 11.0F, new CubeDeformation(0.5F))
                .texOffs(82, 40).addBox(-6.0F, -13.5F, -5.5F, 12.0F, 13.0F, 11.0F, new CubeDeformation(0.52F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(46, 0).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(96, 21).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -6.5F, -7.5F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 1.0F, -2.0F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-5.0F, -2.0F, 0.0F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 1.0F, -2.0F));

        PartDefinition beard = head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(46, 56).addBox(-6.0F, -2.0F, 0.0F, 12.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -3.0F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(46, 16).addBox(0.0F, -1.5F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -9.0F, -0.5F));

        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(46, 16).mirror().addBox(-4.0F, -1.5F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, -9.0F, -0.5F));

        PartDefinition book1 = torso.addOrReplaceChild("book1", CubeListBuilder.create().texOffs(0, 45).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.5F, -0.5F));

        PartDefinition book2 = book1.addOrReplaceChild("book2", CubeListBuilder.create().texOffs(28, 45).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -3.0F, 1.0F));

        PartDefinition book3 = book2.addOrReplaceChild("book3", CubeListBuilder.create().texOffs(50, 42).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -3.0F, -1.0F));

        PartDefinition backpack = torso.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(82, 0).addBox(-8.0F, -6.0F, 0.0F, 16.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.5F, 5.5F));

        PartDefinition bedroll = backpack.addOrReplaceChild("bedroll", CubeListBuilder.create().texOffs(0, 54).addBox(-10.0F, -5.0F, -1.5F, 18.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -4.5F, -0.5F));

        PartDefinition book4 = backpack.addOrReplaceChild("book4", CubeListBuilder.create().texOffs(50, 42).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 4.0F, 0.0F, -0.5672F, -0.9163F));

        PartDefinition book5 = backpack.addOrReplaceChild("book5", CubeListBuilder.create().texOffs(28, 45).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -5.0F, 4.0F, -0.2911F, -0.7723F, -0.3741F));

        PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(46, 30).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.5F, -0.5F));

        PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(46, 30).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 0.5F, -0.5F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animate(((BellsnickelEntity)entity).IDLE_ANIMATION, BellsnickelAnimations.IDLE, ageInTicks, 1.0f);
        this.animate(((BellsnickelEntity)entity).TRADE_ANIMATION, BellsnickelAnimations.LEFT_THROW, ageInTicks, 1.0f);
        this.animate(((BellsnickelEntity)entity).POSE_ANIMATION, BellsnickelAnimations.RIGHT_HOLD, ageInTicks, 1.0f);
        this.animate(((BellsnickelEntity)entity).WALK_ANIMATION, BellsnickelAnimations.WALK, ageInTicks, 1.0f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        bellsnickel.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }


    @Override
    public ModelPart root() {
        return bellsnickel;
    }
}
