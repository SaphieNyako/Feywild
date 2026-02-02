package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.entity.ShroomlingEntity;
import com.saphienyako.feywild.entity.animations.ShroomlingAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ShroomlingModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart shroomling;
    private final ModelPart body;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart torso;
    private final ModelPart front_root;
    private final ModelPart back_root;
    private final ModelPart torso2;
    private final ModelPart head;
    private final ModelPart beard;
    private final ModelPart cap;
    private final ModelPart frill;
    private final ModelPart left_arm;
    private final ModelPart right_arm;

    public ShroomlingModel(ModelPart root) {
        this.shroomling = root.getChild("root");
        this.body = this.shroomling.getChild("body");
        this.left_leg = this.body.getChild("left_leg");
        this.right_leg = this.body.getChild("right_leg");
        this.torso = this.body.getChild("torso");
        this.front_root = this.torso.getChild("front_root");
        this.back_root = this.torso.getChild("back_root");
        this.torso2 = this.torso.getChild("torso2");
        this.head = this.torso2.getChild("head");
        this.beard = this.head.getChild("beard");
        this.cap = this.head.getChild("cap");
        this.frill = this.cap.getChild("frill");
        this.left_arm = this.torso2.getChild("left_arm");
        this.right_arm = this.torso2.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(48, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -4.0F, 0.0F));

        PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, -4.0F, 0.0F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(64, 0).addBox(-5.0F, -10.0F, -3.5F, 10.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition front_root = torso.addOrReplaceChild("front_root", CubeListBuilder.create().texOffs(48, 33).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -3.5F, -0.2618F, 0.0F, 0.0F));

        PartDefinition back_root = torso.addOrReplaceChild("back_root", CubeListBuilder.create().texOffs(48, 33).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 3.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition torso2 = torso.addOrReplaceChild("torso2", CubeListBuilder.create().texOffs(36, 20).addBox(-4.0F, -3.0F, -4.0F, 7.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -10.0F, 1.0F));

        PartDefinition head = torso2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 36).addBox(-4.5F, -7.0F, -4.0F, 9.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(26, 36).addBox(-4.5F, -2.0F, -5.0F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(3.5F, -4.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.5F, -4.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -3.0F, -1.0F));

        PartDefinition beard = head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(36, 29).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cap = head.addOrReplaceChild("cap", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition frill = cap.addOrReplaceChild("frill", CubeListBuilder.create().texOffs(49, 17).addBox(-7.5F, 0.0F, -7.5F, 15.0F, 0.0F, 15.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.15F, -0.5F));

        PartDefinition left_arm = torso2.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -1.0F, -1.0F));

        PartDefinition right_arm = torso2.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -2.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -1.0F, -1.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animate(((ShroomlingEntity)entity).IDLE_ANIMATION, ShroomlingAnimations.IDLE, ageInTicks, 1.0f);
        this.animate(((ShroomlingEntity)entity).SNEEZE_ANIMATION, ShroomlingAnimations.SNEEZE, ageInTicks, 1.0f);
        this.animate(((ShroomlingEntity)entity).WAVE_ANIMATION, ShroomlingAnimations.WAVE, ageInTicks, 1.0f);
        this.animate(((ShroomlingEntity)entity).POSE_ANIMATION, ShroomlingAnimations.POSE, ageInTicks, 1.0f);
        this.animate(((ShroomlingEntity)entity).WALK_ANIMATION, ShroomlingAnimations.WALK, ageInTicks, 1.0f);


    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }


    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        shroomling.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return shroomling;
    }

}
