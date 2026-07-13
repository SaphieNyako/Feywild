package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.entity.SummerPixieEntity;
import com.saphienyako.feywild.entity.TitaniaEntity;
import com.saphienyako.feywild.entity.animations.SummerPixieAnimations;
import com.saphienyako.feywild.entity.animations.TitaniaAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class TitaniaModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart titania;
    private final ModelPart head;
    private final ModelPart righthear;
    private final ModelPart lefthear;
    private final ModelPart hair;
    private final ModelPart leftfronthair;
    private final ModelPart bone;
    private final ModelPart rightfronthair;
    private final ModelPart bone2;
    private final ModelPart backhair;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart bone4;
    private final ModelPart staff;
    private final ModelPart flower;
    private final ModelPart flower1;
    private final ModelPart flower2;
    private final ModelPart flower3;
    private final ModelPart flower4;
    private final ModelPart left_arm;
    private final ModelPart bone3;
    private final ModelPart rightwing;
    private final ModelPart leftwing;
    private final ModelPart skirt;
    private final ModelPart rightskirt;
    private final ModelPart leftskirt;
    private final ModelPart right_leg;
    private final ModelPart left_leg;

    public TitaniaModel(ModelPart root) {
        this.titania = root.getChild("titania");
        this.head = this.titania.getChild("head");
        this.righthear = this.head.getChild("righthear");
        this.lefthear = this.head.getChild("lefthear");
        this.hair = this.head.getChild("hair");
        this.leftfronthair = this.hair.getChild("leftfronthair");
        this.bone = this.leftfronthair.getChild("bone");
        this.rightfronthair = this.hair.getChild("rightfronthair");
        this.bone2 = this.rightfronthair.getChild("bone2");
        this.backhair = this.hair.getChild("backhair");
        this.body = this.titania.getChild("body");
        this.right_arm = this.titania.getChild("right_arm");
        this.bone4 = this.right_arm.getChild("bone4");
        this.staff = this.right_arm.getChild("staff");
        this.flower = this.staff.getChild("flower");
        this.flower1 = this.flower.getChild("flower1");
        this.flower2 = this.flower.getChild("flower2");
        this.flower3 = this.flower.getChild("flower3");
        this.flower4 = this.flower.getChild("flower4");
        this.left_arm = this.titania.getChild("left_arm");
        this.bone3 = this.left_arm.getChild("bone3");
        this.rightwing = this.titania.getChild("rightwing");
        this.leftwing = this.titania.getChild("leftwing");
        this.skirt = this.titania.getChild("skirt");
        this.rightskirt = this.skirt.getChild("rightskirt");
        this.leftskirt = this.skirt.getChild("leftskirt");
        this.right_leg = this.titania.getChild("right_leg");
        this.left_leg = this.titania.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition titania = partdefinition.addOrReplaceChild("titania", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition head = titania.addOrReplaceChild("head", CubeListBuilder.create().texOffs(40, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(50, 22).addBox(-4.5F, -17.0F, -5.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(78, 16).addBox(-4.5F, -17.0F, -5.0F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(78, 16).addBox(4.5F, -17.0F, -5.0F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(29, 86).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

        PartDefinition righthear = head.addOrReplaceChild("righthear", CubeListBuilder.create().texOffs(126, -5).addBox(0.0F, -3.25F, 0.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -3.75F, -2.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition lefthear = head.addOrReplaceChild("lefthear", CubeListBuilder.create().texOffs(126, -5).addBox(0.0F, -3.25F, 0.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -3.75F, -2.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftfronthair = hair.addOrReplaceChild("leftfronthair", CubeListBuilder.create().texOffs(30, 0).addBox(-5.0F, -1.3333F, -1.5167F, 6.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.0F, -9.1667F, -4.2333F, -0.0873F, 0.0F, 0.1745F));

        PartDefinition bone = leftfronthair.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(16, 124).addBox(-1.5F, -0.75F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 0).addBox(-1.5F, 7.5F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.4167F, -0.5167F, 0.0873F, 0.0F, -0.1309F));

        PartDefinition rightfronthair = hair.addOrReplaceChild("rightfronthair", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-1.0F, -1.3333F, -1.5167F, 6.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(1.0F, -9.1667F, -4.2333F, -0.0873F, 0.0F, -0.1745F));

        PartDefinition bone2 = rightfronthair.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(16, 124).mirror().addBox(-1.5F, -0.75F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false)
                .texOffs(0, 0).addBox(-1.5F, 7.5F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.4167F, -0.5167F, 0.0873F, 0.0F, 0.1309F));

        PartDefinition backhair = hair.addOrReplaceChild("backhair", CubeListBuilder.create().texOffs(114, 98).addBox(-6.0F, -1.25F, -0.5F, 12.0F, 14.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(117, 70).addBox(-6.0F, 12.75F, 0.0F, 12.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.75F, 5.5F, 0.1309F, 0.0F, 0.0F));

        PartDefinition body = titania.addOrReplaceChild("body", CubeListBuilder.create().texOffs(96, 54).addBox(-4.5F, -14.6667F, -2.1667F, 9.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(70, 0).mirror().addBox(0.5F, -13.6667F, -3.1667F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(70, 0).addBox(-4.5F, -13.6667F, -3.1667F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 38).addBox(-4.5F, -14.6667F, -2.1667F, 9.0F, 9.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(123, 49).addBox(-3.5F, -5.6667F, -1.1667F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(114, 113).addBox(-3.5F, -5.6667F, -1.1667F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -0.3333F, -1.3333F));

        PartDefinition right_arm = titania.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(96, 70).addBox(-1.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(80, 0).mirror().addBox(-0.5F, 3.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -11.5F, 0.0F, 0.0F, 0.0F, -0.48F));

        PartDefinition bone4 = right_arm.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(30, 6).mirror().addBox(-4.0F, -2.0F, 2.5F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).addBox(2.0F, -2.0F, -2.5F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(30, 6).mirror().addBox(-4.0F, -2.0F, -2.5F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5747F, 5.4975F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition staff = right_arm.addOrReplaceChild("staff", CubeListBuilder.create().texOffs(0, 142).addBox(-1.0F, -31.6667F, -1.0F, 2.0F, 50.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 197).mirror().addBox(1.0F, -31.6667F, 0.0F, 15.0F, 19.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 197).addBox(-16.0F, -31.6667F, 0.0F, 15.0F, 19.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 17.1667F, 3.0F, 0.8537F, -0.4047F, 0.1506F));

        PartDefinition flower = staff.addOrReplaceChild("flower", CubeListBuilder.create().texOffs(18, 171).addBox(10.0F, -23.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(37, 167).addBox(10.0F, -31.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(52, 164).addBox(13.0F, -31.0F, -3.0F, 0.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, -16.6667F, 0.0F));

        PartDefinition flower1 = flower.addOrReplaceChild("flower1", CubeListBuilder.create().texOffs(16, 234).addBox(0.025F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 246).addBox(2.025F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 230).addBox(10.025F, -0.5F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 239).addBox(12.025F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.975F, -22.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition flower2 = flower.addOrReplaceChild("flower2", CubeListBuilder.create().texOffs(16, 234).addBox(0.025F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 246).addBox(2.025F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 230).addBox(10.025F, -0.5F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 239).addBox(12.025F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.975F, -22.0F, 3.0F, -1.5708F, -1.2217F, 1.5708F));

        PartDefinition flower3 = flower.addOrReplaceChild("flower3", CubeListBuilder.create().texOffs(16, 234).addBox(0.025F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 246).addBox(2.025F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 230).addBox(10.025F, -0.5F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 239).addBox(12.025F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.975F, -22.0F, 0.0F, 3.1416F, 0.0F, 2.7925F));

        PartDefinition flower4 = flower.addOrReplaceChild("flower4", CubeListBuilder.create().texOffs(16, 234).addBox(0.025F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 246).addBox(2.025F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 230).addBox(10.025F, -0.5F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 239).addBox(12.025F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.975F, -22.0F, -3.0F, 1.5708F, 1.2217F, 1.5708F));

        PartDefinition left_arm = titania.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(96, 70).mirror().addBox(-5.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(80, 0).addBox(-3.5F, 3.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -11.5F, 0.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition bone3 = left_arm.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(30, 6).mirror().addBox(-4.0F, -2.0F, 2.5F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).addBox(2.0F, -2.0F, -2.5F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(30, 6).mirror().addBox(-4.0F, -2.0F, -2.5F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5753F, 5.4975F, 0.0F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition rightwing = titania.addOrReplaceChild("rightwing", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -30.0F, 0.0F, 0.0F, 62.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, -4.0F, 6.5F, 0.0F, 0.7854F, 0.0F));

        PartDefinition leftwing = titania.addOrReplaceChild("leftwing", CubeListBuilder.create().texOffs(0, 0).addBox(0.4142F, -30.0F, 0.0F, 0.0F, 62.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -4.0F, 6.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition skirt = titania.addOrReplaceChild("skirt", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightskirt = skirt.addOrReplaceChild("rightskirt", CubeListBuilder.create().texOffs(96, 0).addBox(0.0F, 0.0F, -3.5F, 10.0F, 29.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition cube_r1 = rightskirt.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 86).addBox(0.0F, 0.0F, -3.5F, 10.0F, 29.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition leftskirt = skirt.addOrReplaceChild("leftskirt", CubeListBuilder.create().texOffs(96, 0).mirror().addBox(-10.0F, 0.0F, -3.5F, 10.0F, 29.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r2 = leftskirt.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 86).mirror().addBox(-10.0F, 0.0F, -3.5F, 10.0F, 29.0F, 9.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition right_leg = titania.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(75, 36).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 19.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(75, 66).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 19.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(2.5F, 0.5F, 0.0F));

        PartDefinition left_leg = titania.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(75, 36).mirror().addBox(-2.0F, -0.5F, -2.0F, 4.0F, 19.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(75, 66).mirror().addBox(-2.0F, -0.5F, -2.0F, 4.0F, 19.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(-2.5F, 0.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animate(((TitaniaEntity)entity).IDLE_ANIMATION, TitaniaAnimations.FLYING_IDLE, ageInTicks, 1.0f);
        this.animate(((TitaniaEntity)entity).FLYING_ANIMATION, TitaniaAnimations.FLYING, ageInTicks, 1.0f);
        this.animate(((TitaniaEntity)entity).CASTING_ANIMATION, TitaniaAnimations.CASTING, ageInTicks, 1.0f);
        this.animate(((TitaniaEntity)entity).ENCHANTING_ANIMATION, TitaniaAnimations.ENCHANTING, ageInTicks, 1.0f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        titania.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public @NotNull ModelPart root() {
        return titania;
    }
}
