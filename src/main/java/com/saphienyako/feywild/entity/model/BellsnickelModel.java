package com.saphienyako.feywild.entity.model;

import com.saphienyako.feywild.entity.animations.BellsnickelAnimations;
import com.saphienyako.feywild.entity.state.BellsnickelRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

public class BellsnickelModel extends EntityModel<BellsnickelRenderState> {

    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation tradeAnimation;
    private final KeyframeAnimation poseAnimation;
    private final KeyframeAnimation walkAnimation;

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
        super(root, RenderTypes::entityCutout);

        this.idleAnimation = BellsnickelAnimations.IDLE.bake(root);
        this.tradeAnimation = BellsnickelAnimations.LEFT_THROW.bake(root);
        this.poseAnimation = BellsnickelAnimations.RIGHT_HOLD.bake(root);
        this.walkAnimation = BellsnickelAnimations.WALK.bake(root);

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
    public void setupAnim(BellsnickelRenderState state) {
        super.setupAnim(state);

        this.applyHeadRotation(state.yRot, state.xRot, state.ageInTicks);

        this.idleAnimation.apply(state.idleAnimation, state.ageInTicks, 1.0F);
        this.tradeAnimation.apply(state.tradeAnimation, state.ageInTicks, 1.0F);
        this.poseAnimation.apply(state.poseAnimation, state.ageInTicks, 1.0F);
        this.walkAnimation.apply(state.walkAnimation, state.ageInTicks, 1.0F);
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
