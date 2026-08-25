package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.entity.MabEntity;
import com.saphienyako.feywild.entity.OberonEntity;
import com.saphienyako.feywild.entity.animations.MabAnimations;
import com.saphienyako.feywild.entity.animations.OberonAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class OberonModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart oberon;
    private final ModelPart body;
    private final ModelPart torso;
    private final ModelPart torso2;
    private final ModelPart cloth;
    private final ModelPart cloth2;
    private final ModelPart chest;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart left_hair;
    private final ModelPart left_hair2;
    private final ModelPart left_hair3;
    private final ModelPart right_hair;
    private final ModelPart right_hair2;
    private final ModelPart right_hair3;
    private final ModelPart hair;
    private final ModelPart hair2;
    private final ModelPart hair3;
    private final ModelPart left_antler;
    private final ModelPart left_leaf1;
    private final ModelPart left_vine1;
    private final ModelPart right_antler;
    private final ModelPart right_leaf1;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart tail;
    private final ModelPart left_frontleg;
    private final ModelPart left_frontleg2;
    private final ModelPart right_frontleg;
    private final ModelPart right_frontleg2;
    private final ModelPart left_backleg;
    private final ModelPart left_backleg2;
    private final ModelPart right_backleg;
    private final ModelPart right_backleg2;

    public OberonModel(ModelPart oberon) {
        this.oberon = oberon.getChild("oberon");
        this.body = this.oberon.getChild("body");
        this.torso = this.body.getChild("torso");
        this.torso2 = this.torso.getChild("torso2");
        this.cloth = this.torso2.getChild("cloth");
        this.cloth2 = this.cloth.getChild("cloth2");
        this.chest = this.torso2.getChild("chest");
        this.neck = this.chest.getChild("neck");
        this.head = this.neck.getChild("head");
        this.left_hair = this.head.getChild("left_hair");
        this.left_hair2 = this.left_hair.getChild("left_hair2");
        this.left_hair3 = this.left_hair2.getChild("left_hair3");
        this.right_hair = this.head.getChild("right_hair");
        this.right_hair2 = this.right_hair.getChild("right_hair2");
        this.right_hair3 = this.right_hair2.getChild("right_hair3");
        this.hair = this.head.getChild("hair");
        this.hair2 = this.hair.getChild("hair2");
        this.hair3 = this.hair2.getChild("hair3");
        this.left_antler = this.head.getChild("left_antler");
        this.left_leaf1 = this.left_antler.getChild("left_leaf1");
        this.left_vine1 = this.left_antler.getChild("left_vine1");
        this.right_antler = this.head.getChild("right_antler");
        this.right_leaf1 = this.right_antler.getChild("right_leaf1");
        this.left_ear = this.head.getChild("left_ear");
        this.right_ear = this.head.getChild("right_ear");
        this.left_arm = this.chest.getChild("left_arm");
        this.right_arm = this.chest.getChild("right_arm");
        this.tail = this.torso.getChild("tail");
        this.left_frontleg = this.body.getChild("left_frontleg");
        this.left_frontleg2 = this.left_frontleg.getChild("left_frontleg2");
        this.right_frontleg = this.body.getChild("right_frontleg");
        this.right_frontleg2 = this.right_frontleg.getChild("right_frontleg2");
        this.left_backleg = this.body.getChild("left_backleg");
        this.left_backleg2 = this.left_backleg.getChild("left_backleg2");
        this.right_backleg = this.body.getChild("right_backleg");
        this.right_backleg2 = this.right_backleg.getChild("right_backleg2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition oberon = partdefinition.addOrReplaceChild("oberon", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = oberon.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -22.0F, -1.0F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-6.5F, -7.0F, -16.5F, 13.0F, 12.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 5.0F));

        PartDefinition torso2 = torso.addOrReplaceChild("torso2", CubeListBuilder.create().texOffs(73, 99).addBox(-4.5F, -12.0F, -3.0F, 9.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(4, 107).addBox(-5.0F, -8.0F, -3.5F, 10.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -15.5F));

        PartDefinition cloth = torso2.addOrReplaceChild("cloth", CubeListBuilder.create().texOffs(49, 93).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -3.5F));

        PartDefinition cloth2 = cloth.addOrReplaceChild("cloth2", CubeListBuilder.create().texOffs(49, 100).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition chest = torso2.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(6, 41).addBox(-7.0F, -9.0F, -4.0F, 14.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(22, 65).addBox(-7.0F, -9.0F, -4.0F, 14.0F, 9.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -11.0F, 0.0F));

        PartDefinition neck = chest.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(81, 36).addBox(-3.0F, -3.0F, -2.5F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.25F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(94, 110).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -0.25F));

        PartDefinition left_hair = head.addOrReplaceChild("left_hair", CubeListBuilder.create().texOffs(0, 107).addBox(-2.0F, -1.0F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(3.0F, -8.0F, -4.0F));

        PartDefinition left_hair2 = left_hair.addOrReplaceChild("left_hair2", CubeListBuilder.create().texOffs(0, 112).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(3.0F, 4.0F, 0.0F));

        PartDefinition left_hair3 = left_hair2.addOrReplaceChild("left_hair3", CubeListBuilder.create().texOffs(0, 120).addBox(-0.5F, 0.0F, 0.0F, 2.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(-1.5F, 8.0F, 0.0F));

        PartDefinition right_hair = head.addOrReplaceChild("right_hair", CubeListBuilder.create().texOffs(0, 107).mirror().addBox(-3.0F, -1.0F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-3.0F, -8.0F, -4.0F));

        PartDefinition right_hair2 = right_hair.addOrReplaceChild("right_hair2", CubeListBuilder.create().texOffs(0, 112).mirror().addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-3.0F, 4.0F, 0.0F));

        PartDefinition right_hair3 = right_hair2.addOrReplaceChild("right_hair3", CubeListBuilder.create().texOffs(0, 120).mirror().addBox(-1.5F, 0.0F, 0.0F, 2.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(1.5F, 8.0F, 0.0F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(0, 5).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 10.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -9.0F, 5.0F));

        PartDefinition hair2 = hair.addOrReplaceChild("hair2", CubeListBuilder.create().texOffs(85, 62).addBox(-4.0F, -0.5F, -1.5F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -1.5F));

        PartDefinition hair3 = hair2.addOrReplaceChild("hair3", CubeListBuilder.create().texOffs(112, 57).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.5F, 0.0F));

        PartDefinition left_antler = head.addOrReplaceChild("left_antler", CubeListBuilder.create().texOffs(14, -2).addBox(0.0F, -3.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(71, 59).addBox(9.0F, -6.0F, -3.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(53, 49).addBox(0.0F, -9.0F, 5.0F, 16.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(71, 64).addBox(0.0F, -6.0F, 2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 46).addBox(16.0F, -9.0F, -3.0F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(4, 118).addBox(0.0F, -4.0F, -3.0F, 16.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -8.0F, 1.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r1 = left_antler.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 2).mirror().addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(1.5F, -6.5F, 5.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition left_leaf1 = left_antler.addOrReplaceChild("left_leaf1", CubeListBuilder.create().texOffs(0, 35).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -6.0F, -1.0F, 0.0F, 0.0F, -1.0908F));

        PartDefinition left_vine1 = left_antler.addOrReplaceChild("left_vine1", CubeListBuilder.create().texOffs(28, 102).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(16, 2).addBox(4.0F, 1.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(8.0F, -3.0F, -2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition right_antler = head.addOrReplaceChild("right_antler", CubeListBuilder.create().texOffs(71, 64).mirror().addBox(0.0F, -6.0F, 2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(71, 59).mirror().addBox(-14.0F, -6.0F, -3.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(64, 46).mirror().addBox(-16.0F, -9.0F, -3.0F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(49, 43).mirror().addBox(-16.0F, -9.0F, 5.0F, 16.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(14, -2).mirror().addBox(0.0F, -3.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(10, 18).mirror().addBox(-11.0F, -10.0F, 5.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(16, 2).mirror().addBox(-14.0F, -4.0F, -3.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(80, 48).mirror().addBox(-16.0F, -4.0F, -3.0F, 16.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -8.0F, 1.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition right_leaf1 = right_antler.addOrReplaceChild("right_leaf1", CubeListBuilder.create().texOffs(0, 35).mirror().addBox(-2.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, -3.0F, -3.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.5F, -1.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -3.5F, 1.0F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-5.0F, -1.5F, -1.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -3.5F, 1.0F));

        PartDefinition left_arm = chest.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(101, 0).addBox(0.0F, -1.5F, -2.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(38, 107).addBox(-2.0F, -2.5F, -3.0F, 7.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(112, 65).addBox(0.0F, 1.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(7.0F, -7.5F, 0.0F));

        PartDefinition right_arm = chest.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(101, 0).mirror().addBox(-4.0F, -1.5F, -2.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(112, 65).mirror().addBox(-4.0F, 1.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(38, 107).mirror().addBox(-5.0F, -2.5F, -3.0F, 7.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 18).mirror().addBox(-10.0F, -4.5F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, -7.5F, 0.0F));

        PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(22, 85).addBox(-2.5F, -3.5F, -1.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 84).addBox(0.0F, -3.5F, -1.0F, 0.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.5F, 5.5F));

        PartDefinition left_frontleg = body.addOrReplaceChild("left_frontleg", CubeListBuilder.create().texOffs(77, 0).addBox(-3.0F, -3.25F, -3.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -0.75F, -9.5F));

        PartDefinition left_frontleg2 = left_frontleg.addOrReplaceChild("left_frontleg2", CubeListBuilder.create().texOffs(89, 19).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.75F, -2.0F));

        PartDefinition right_frontleg = body.addOrReplaceChild("right_frontleg", CubeListBuilder.create().texOffs(77, 0).mirror().addBox(-3.0F, -3.25F, -3.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, -0.75F, -9.5F));

        PartDefinition right_frontleg2 = right_frontleg.addOrReplaceChild("right_frontleg2", CubeListBuilder.create().texOffs(89, 19).mirror().addBox(-2.0F, 0.0F, 0.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 9.75F, -2.0F));

        PartDefinition left_backleg = body.addOrReplaceChild("left_backleg", CubeListBuilder.create().texOffs(49, 0).addBox(-3.0F, -6.5F, -4.0F, 6.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 3.5F, 8.5F));

        PartDefinition left_backleg2 = left_backleg.addOrReplaceChild("left_backleg2", CubeListBuilder.create().texOffs(73, 19).addBox(-2.0F, -0.5F, 0.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 1.0F));

        PartDefinition right_backleg = body.addOrReplaceChild("right_backleg", CubeListBuilder.create().texOffs(49, 0).mirror().addBox(-3.0F, -6.5F, -4.0F, 6.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, 3.5F, 8.5F));

        PartDefinition right_backleg2 = right_backleg.addOrReplaceChild("right_backleg2", CubeListBuilder.create().texOffs(73, 19).mirror().addBox(-2.0F, -0.5F, 0.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 6.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animate(((OberonEntity)entity).IDLE_ANIMATION, OberonAnimations.IDLE, ageInTicks, 1.0f);
        this.animate(((OberonEntity)entity).WALKING_ANIMATION, OberonAnimations.WALKING, ageInTicks, 1.0f);
        this.animate(((OberonEntity)entity).CHARGING_ANIMATION, OberonAnimations.CHARGING, ageInTicks, 1.0f);
        this.animate(((OberonEntity)entity).KICKING_ANIMATION, OberonAnimations.KICKING, ageInTicks, 1.0f);
        this.animate(((OberonEntity)entity).REARING_ANIMATION, OberonAnimations.REARING, ageInTicks, 1.0f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        oberon.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }

    @Override
    public ModelPart root() {
        return oberon;
    }
}
