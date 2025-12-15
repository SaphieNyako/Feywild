package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.entity.WinterPixieEntity;
import com.saphienyako.feywild.entity.animations.WinterPixieAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class WinterPixieModel <T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart winter_pixie;
    private final ModelPart head;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart left_hair;
    private final ModelPart right_hair;
    private final ModelPart hair;
    private final ModelPart body;
    private final ModelPart right_skirt;
    private final ModelPart left_skirt;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart right_wing;
    private final ModelPart left_wing;

    public WinterPixieModel(ModelPart root) {
        this.winter_pixie = root.getChild("total");
        this.head = this.winter_pixie.getChild("head");
        this.left_ear = this.head.getChild("left_ear");
        this.right_ear = this.head.getChild("right_ear");
        this.left_hair = this.head.getChild("left_hair");
        this.right_hair = this.head.getChild("right_hair");
        this.hair = this.head.getChild("hair");
        this.body = this.winter_pixie.getChild("body");
        this.right_skirt = this.body.getChild("right_skirt");
        this.left_skirt = this.body.getChild("left_skirt");
        this.right_arm = this.body.getChild("right_arm");
        this.left_arm = this.body.getChild("left_arm");
        this.left_leg = this.body.getChild("left_leg");
        this.right_leg = this.body.getChild("right_leg");
        this.right_wing = this.body.getChild("right_wing");
        this.left_wing = this.body.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition total = partdefinition.addOrReplaceChild("total", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition head = total.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -3.5F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(34, 25).addBox(-3.5F, -10.0F, -1.0F, 7.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(5, 6).addBox(4.0F, -3.0F, -3.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(5, 6).addBox(-5.0F, -3.0F, -3.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(31, 0).addBox(-4.0F, -7.0F, -3.5F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.1F))
                .texOffs(0, 41).addBox(-4.0F, -7.0F, -3.5F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(56, -2).mirror().addBox(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -2.0F, -1.5F, 0.0F, 0.48F, 0.0F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(56, -2).mirror().addBox(0.0F, -3.0F, 0.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -2.0F, -1.5F, 0.0F, -0.48F, 0.0F));

        PartDefinition left_hair = head.addOrReplaceChild("left_hair", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -6.0F, -3.6F, -0.1229F, 0.004F, -0.3084F));

        PartDefinition right_hair = head.addOrReplaceChild("right_hair", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -6.0F, -3.6F, -0.1229F, -0.004F, 0.3084F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(9, 35).addBox(-3.5F, -1.5F, 0.0F, 7.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0337F, 3.7354F, 0.1309F, 0.0F, 0.0F));

        PartDefinition body = total.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 14).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 14).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 6.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition right_skirt = body.addOrReplaceChild("right_skirt", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-3.0F, 0.0F, -2.5F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 5.0F, 0.0F, 0.0057F, 0.1308F, 0.044F));

        PartDefinition left_skirt = body.addOrReplaceChild("left_skirt", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, 0.0F, -2.5F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 5.0F, 0.0F, 0.0057F, -0.1308F, -0.044F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 24).mirror().addBox(-2.5F, 1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(23, 0).mirror().addBox(-3.0F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 1.0F, 0.0F, -0.2104F, -0.1838F, 0.3494F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 24).addBox(0.5F, 1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(23, 0).addBox(0.0F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 1.0F, 0.0F, -0.2104F, 0.1838F, -0.3494F));

        PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(25, 24).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 6.0F, 0.0F, 0.0436F, 0.0F, 0.0436F));

        PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(25, 24).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 6.0F, 0.0F, 0.1745F, 0.0F, -0.0436F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(24, 16).mirror().addBox(-0.034F, -15.5055F, 0.0988F, 0.0F, 28.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 1.5F, 3.5F, -0.0649F, -0.7865F, -0.0482F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(24, 16).addBox(0.034F, -15.5055F, 0.0988F, 0.0F, 28.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.5F, 3.5F, -0.0649F, 0.7865F, 0.0482F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animate(((WinterPixieEntity)entity).IDLE_ANIMATION, WinterPixieAnimations.IDLE, ageInTicks, 2f);
        this.animate(((WinterPixieEntity)entity).SPELL_CASTING_ANIMATION, WinterPixieAnimations.SPELL_CASTING, ageInTicks, 1.0f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        winter_pixie.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }


    @Override
    public ModelPart root() {
        return winter_pixie;
    }
}
