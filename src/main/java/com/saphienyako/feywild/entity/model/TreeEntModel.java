package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.entity.ShroomlingEntity;
import com.saphienyako.feywild.entity.animations.ShroomlingAnimations;
import com.saphienyako.feywild.entity.animations.TreeEntAnimations;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public class TreeEntModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart tree_ent;
    private final ModelPart body;
    private final ModelPart upperbody;
    private final ModelPart ice_bottom;
    private final ModelPart ice_middle;
    private final ModelPart ice_top;
    private final ModelPart sunflower_v_1;
    private final ModelPart sunflower_v_3;
    private final ModelPart sunflower_v_2;
    private final ModelPart sunflower_v_4;
    private final ModelPart crows;
    private final ModelPart crow1;
    private final ModelPart chead;
    private final ModelPart cwing1;
    private final ModelPart cwing2;
    private final ModelPart crow2;
    private final ModelPart chead2;
    private final ModelPart cwing3;
    private final ModelPart cwing4;
    private final ModelPart head;
    private final ModelPart beard;
    private final ModelPart left_arm;
    private final ModelPart left_arm2;
    private final ModelPart left_finger1;
    private final ModelPart left_finger2;
    private final ModelPart left_finger3;
    private final ModelPart bee_nest;
    private final ModelPart ice_stalagmite4;
    private final ModelPart ice_stalagmite5;
    private final ModelPart ice_stalagmite6;
    private final ModelPart right_arm;
    private final ModelPart ice_stalagmite;
    private final ModelPart ice_stalagmite2;
    private final ModelPart ice_stalagmite3;
    private final ModelPart right_arm2;
    private final ModelPart right_finger1;
    private final ModelPart right_finger2;
    private final ModelPart right_finger3;
    private final ModelPart sunflower_o_4;
    private final ModelPart sunflower_o_5;
    private final ModelPart sunflower_o_6;
    private final ModelPart sunflower_o_7;
    private final ModelPart bee_nest2;
    private final ModelPart sunflower_o_1;
    private final ModelPart sunflower_o_2;
    private final ModelPart sunflower_o_3;
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

    public TreeEntModel(ModelPart root) {
        this.tree_ent = root.getChild("total");
        this.body = this.tree_ent.getChild("body");
        this.upperbody = this.body.getChild("upperbody");
        this.ice_bottom = this.upperbody.getChild("ice_bottom");
        this.ice_middle = this.ice_bottom.getChild("ice_middle");
        this.ice_top = this.ice_middle.getChild("ice_top");
        this.sunflower_v_1 = this.upperbody.getChild("sunflower_v_1");
        this.sunflower_v_3 = this.upperbody.getChild("sunflower_v_3");
        this.sunflower_v_2 = this.upperbody.getChild("sunflower_v_2");
        this.sunflower_v_4 = this.upperbody.getChild("sunflower_v_4");
        this.crows = this.upperbody.getChild("crows");
        this.crow1 = this.crows.getChild("crow1");
        this.chead = this.crow1.getChild("chead");
        this.cwing1 = this.crow1.getChild("cwing1");
        this.cwing2 = this.crow1.getChild("cwing2");
        this.crow2 = this.crows.getChild("crow2");
        this.chead2 = this.crow2.getChild("chead2");
        this.cwing3 = this.crow2.getChild("cwing3");
        this.cwing4 = this.crow2.getChild("cwing4");
        this.head = this.tree_ent.getChild("head");
        this.beard = this.head.getChild("beard");
        this.left_arm = this.tree_ent.getChild("left_arm");
        this.left_arm2 = this.left_arm.getChild("left_arm2");
        this.left_finger1 = this.left_arm2.getChild("left_finger1");
        this.left_finger2 = this.left_arm2.getChild("left_finger2");
        this.left_finger3 = this.left_arm2.getChild("left_finger3");
        this.bee_nest = this.left_arm.getChild("bee_nest");
        this.ice_stalagmite4 = this.left_arm.getChild("ice_stalagmite4");
        this.ice_stalagmite5 = this.left_arm.getChild("ice_stalagmite5");
        this.ice_stalagmite6 = this.left_arm.getChild("ice_stalagmite6");
        this.right_arm = this.tree_ent.getChild("right_arm");
        this.ice_stalagmite = this.right_arm.getChild("ice_stalagmite");
        this.ice_stalagmite2 = this.right_arm.getChild("ice_stalagmite2");
        this.ice_stalagmite3 = this.right_arm.getChild("ice_stalagmite3");
        this.right_arm2 = this.right_arm.getChild("right_arm2");
        this.right_finger1 = this.right_arm2.getChild("right_finger1");
        this.right_finger2 = this.right_arm2.getChild("right_finger2");
        this.right_finger3 = this.right_arm2.getChild("right_finger3");
        this.sunflower_o_4 = this.right_arm2.getChild("sunflower_o_4");
        this.sunflower_o_5 = this.right_arm2.getChild("sunflower_o_5");
        this.sunflower_o_6 = this.right_arm2.getChild("sunflower_o_6");
        this.sunflower_o_7 = this.right_arm2.getChild("sunflower_o_7");
        this.bee_nest2 = this.right_arm.getChild("bee_nest2");
        this.sunflower_o_1 = this.right_arm.getChild("sunflower_o_1");
        this.sunflower_o_2 = this.right_arm.getChild("sunflower_o_2");
        this.sunflower_o_3 = this.right_arm.getChild("sunflower_o_3");
        this.left_leg = this.tree_ent.getChild("left_leg");
        this.left_leg2 = this.left_leg.getChild("left_leg2");
        this.left_finger_f1 = this.left_leg2.getChild("left_finger_f1");
        this.left_finger_f2 = this.left_leg2.getChild("left_finger_f2");
        this.left_finger_f3 = this.left_leg2.getChild("left_finger_f3");
        this.right_leg = this.tree_ent.getChild("right_leg");
        this.right_leg2 = this.right_leg.getChild("right_leg2");
        this.right_finger_f1 = this.right_leg2.getChild("right_finger_f1");
        this.right_finger_f2 = this.right_leg2.getChild("right_finger_f2");
        this.right_finger_f3 = this.right_leg2.getChild("right_finger_f3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition total = partdefinition.addOrReplaceChild("total", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition body = total.addOrReplaceChild("body", CubeListBuilder.create().texOffs(88, 0).addBox(-9.0F, -5.0F, -7.0F, 18.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(114, 62).addBox(-7.0F, -14.0F, -5.0F, 14.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upperbody = body.addOrReplaceChild("upperbody", CubeListBuilder.create().texOffs(0, 33).addBox(-16.0F, -19.0F, -9.0F, 32.0F, 19.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-17.0F, -20.0F, -10.0F, 34.0F, 13.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));

        PartDefinition ice_bottom = upperbody.addOrReplaceChild("ice_bottom", CubeListBuilder.create().texOffs(156, 33).addBox(-16.0F, -3.0F, -9.0F, 32.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 0.0F));

        PartDefinition ice_middle = ice_bottom.addOrReplaceChild("ice_middle", CubeListBuilder.create().texOffs(178, 56).addBox(-11.5F, -8.0F, -8.0F, 23.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -3.0F, 0.0F));

        PartDefinition ice_top = ice_middle.addOrReplaceChild("ice_top", CubeListBuilder.create().texOffs(214, 9).addBox(-5.5F, -9.0F, -5.0F, 11.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -8.0F, 0.0F));

        PartDefinition sunflower_v_1 = upperbody.addOrReplaceChild("sunflower_v_1", CubeListBuilder.create().texOffs(39, 163).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, -20.0F, -10.0F, 0.5232F, 0.0218F, -0.0378F));

        PartDefinition sunflower_v_3 = upperbody.addOrReplaceChild("sunflower_v_3", CubeListBuilder.create().texOffs(39, 163).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -21.0F, -4.0F, 0.2608F, -0.0226F, 0.0843F));

        PartDefinition sunflower_v_2 = upperbody.addOrReplaceChild("sunflower_v_2", CubeListBuilder.create().texOffs(39, 163).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -21.0F, 9.0F, -0.4363F, 0.2618F, 0.0F));

        PartDefinition sunflower_v_4 = upperbody.addOrReplaceChild("sunflower_v_4", CubeListBuilder.create().texOffs(39, 163).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -21.0F, 5.0F, -0.2114F, -0.3646F, 0.0872F));

        PartDefinition crows = upperbody.addOrReplaceChild("crows", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition crow1 = crows.addOrReplaceChild("crow1", CubeListBuilder.create().texOffs(147, 62).addBox(-2.0F, -6.0F, 0.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(162, 74).addBox(-1.5F, -1.0F, 4.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -19.0F, -8.0F, 0.48F, 0.0F, 0.0F));

        PartDefinition chead = crow1.addOrReplaceChild("chead", CubeListBuilder.create().texOffs(163, 62).addBox(-1.5F, -3.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(165, 68).addBox(-0.5F, -2.0F, -5.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(170, 59).addBox(-0.5F, -2.0F, -4.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.25F, 1.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cwing1 = crow1.addOrReplaceChild("cwing1", CubeListBuilder.create().texOffs(138, 63).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -6.0F, 2.0F));

        PartDefinition cwing2 = crow1.addOrReplaceChild("cwing2", CubeListBuilder.create().texOffs(138, 63).addBox(0.0F, 0.0F, -1.5F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -6.0F, 2.0F));

        PartDefinition crow2 = crows.addOrReplaceChild("crow2", CubeListBuilder.create().texOffs(147, 62).addBox(-2.0F, -6.0F, 0.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(162, 74).addBox(-1.5F, -1.0F, 4.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -19.0F, -8.0F, 0.48F, 0.0F, 0.0F));

        PartDefinition chead2 = crow2.addOrReplaceChild("chead2", CubeListBuilder.create().texOffs(163, 62).addBox(-1.5F, -3.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(165, 68).addBox(-0.5F, -2.0F, -5.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(170, 59).addBox(-0.5F, -2.0F, -4.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.25F, 1.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cwing3 = crow2.addOrReplaceChild("cwing3", CubeListBuilder.create().texOffs(138, 63).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -6.0F, 2.0F));

        PartDefinition cwing4 = crow2.addOrReplaceChild("cwing4", CubeListBuilder.create().texOffs(138, 63).addBox(0.0F, 0.0F, -1.5F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -6.0F, 2.0F));

        PartDefinition head = total.addOrReplaceChild("head", CubeListBuilder.create().texOffs(44, 112).addBox(-6.0F, -7.0F, -12.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(123, 219).addBox(-6.0F, -7.0F, -12.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.1F))
                .texOffs(126, 245).addBox(-5.0F, -8.0F, -11.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(92, 246).addBox(-3.4F, -10.0F, -10.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(126, 81).addBox(-6.0F, -9.0F, -12.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(132, 114).addBox(-7.0F, -4.0F, -14.0F, 14.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(88, 0).addBox(-2.0F, -1.0F, -14.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(188, 64).addBox(-1.0F, 3.0F, -14.5F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 70).addBox(-10.0F, -4.0F, -8.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(150, 140).addBox(-10.0F, -6.0F, -8.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 78).addBox(6.0F, -5.0F, -8.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(60, 79).addBox(7.0F, -8.0F, -8.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, -9.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 154).addBox(0.25F, -3.5F, -3.5F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7406F, -6.023F, -11.8802F, 0.2439F, 0.3241F, -0.4077F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(188, 64).addBox(0.0F, -2.0F, -1.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 5.0F, -13.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition beard = head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(118, 128).addBox(-8.0F, 1.0F, -12.1F, 16.0F, 29.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 124).addBox(-6.1F, 1.0F, -12.0F, 0.0F, 18.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 117).addBox(6.1F, 1.0F, -12.0F, 0.0F, 18.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = total.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(84, 128).addBox(0.0F, -2.0F, -4.0F, 9.0F, 24.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(33, 96).addBox(0.0F, -5.0F, -4.0F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(144, 120).addBox(9.0F, 2.0F, -2.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(12.0F, -14.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(82, 33).addBox(9.0F, 12.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 70).addBox(3.0436F, -14.999F, -8.0F, 22.0F, 10.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(88, 164).addBox(3.0436F, -14.999F, -8.0F, 22.0F, 10.0F, 16.0F, new CubeDeformation(0.1F))
                .texOffs(92, 112).addBox(7.0436F, -18.999F, -6.0F, 14.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(100, 62).addBox(13.0F, -2.0F, -3.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(150, 134).addBox(11.0F, -1.0F, 1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(138, 19).addBox(-1.0F, 2.0F, -7.0F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(60, 70).addBox(1.0F, 5.0F, 3.0F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(48, 136).addBox(8.0F, 10.0F, -5.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(66, 148).addBox(-2.0F, 12.0F, -6.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 129).addBox(-1.0F, 11.0F, 3.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -29.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition left_arm2 = left_arm.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(76, 70).addBox(-6.5F, 0.0F, -6.0F, 13.0F, 30.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 214).addBox(-6.5F, 0.0F, -6.0F, 13.0F, 30.0F, 12.0F, new CubeDeformation(0.1F))
                .texOffs(94, 19).addBox(-7.5F, -1.0F, -7.0F, 15.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(48, 148).addBox(-7.5F, 10.0F, -8.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 147).addBox(-7.5F, 15.0F, 3.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(150, 128).addBox(-4.5F, 15.0F, -9.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(88, 7).addBox(-4.5F, 11.0F, 6.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(44, 107).addBox(4.5F, 12.0F, -7.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(64, 104).addBox(5.5F, 17.0F, 4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(59, 96).addBox(4.5F, 25.0F, -7.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 22.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition left_finger1 = left_arm2.addOrReplaceChild("left_finger1", CubeListBuilder.create().texOffs(139, 39).addBox(-8.0F, -2.5F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 28.5F, -5.5F));

        PartDefinition left_finger2 = left_arm2.addOrReplaceChild("left_finger2", CubeListBuilder.create().texOffs(138, 0).addBox(-10.0F, 0.0F, -2.5F, 10.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 30.0F, -3.0F));

        PartDefinition left_finger3 = left_arm2.addOrReplaceChild("left_finger3", CubeListBuilder.create().texOffs(82, 39).addBox(-10.0F, 0.0F, -2.5F, 10.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 30.0F, 3.0F));

        PartDefinition bee_nest = left_arm.addOrReplaceChild("bee_nest", CubeListBuilder.create().texOffs(199, 0).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(169, 0).addBox(-3.5F, 7.0F, -3.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(169, 13).addBox(-4.5F, 2.0F, -4.5F, 9.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(20.5F, -6.0F, -5.5F));

        PartDefinition ice_stalagmite4 = left_arm.addOrReplaceChild("ice_stalagmite4", CubeListBuilder.create().texOffs(0, 97).mirror().addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(14.4564F, -5.999F, -6.0F));

        PartDefinition cube_r3 = ice_stalagmite4.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 97).mirror().addBox(-10.5436F, -3.999F, 19.5F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(19.5436F, 3.999F, 8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition ice_stalagmite5 = left_arm.addOrReplaceChild("ice_stalagmite5", CubeListBuilder.create().texOffs(0, 100).mirror().addBox(-2.5F, 0.0F, 0.0F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(22.4564F, -5.999F, 5.0F));

        PartDefinition cube_r4 = ice_stalagmite5.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 100).mirror().addBox(-10.5436F, -3.999F, 19.5F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(19.5436F, 3.999F, 8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition ice_stalagmite6 = left_arm.addOrReplaceChild("ice_stalagmite6", CubeListBuilder.create().texOffs(0, 100).mirror().addBox(-2.5F, 0.0F, 0.0F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.4564F, -5.999F, 5.0F));

        PartDefinition cube_r5 = ice_stalagmite6.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 100).mirror().addBox(-10.5436F, -3.999F, 19.5F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(19.5436F, 3.999F, 8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition right_arm = total.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(84, 128).mirror().addBox(-9.0F, -2.0F, -4.0F, 9.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(57, 224).mirror().addBox(-9.0F, -2.0F, -4.0F, 9.0F, 24.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(33, 96).mirror().addBox(-9.0F, -5.0F, -4.0F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(144, 120).mirror().addBox(-16.0F, 2.0F, -2.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-16.0F, -14.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(82, 33).mirror().addBox(-12.0F, 12.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 70).mirror().addBox(-25.0436F, -14.999F, -8.0F, 22.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(88, 164).mirror().addBox(-25.0436F, -14.999F, -8.0F, 22.0F, 10.0F, 16.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(92, 112).mirror().addBox(-21.0436F, -18.999F, -6.0F, 14.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(100, 62).mirror().addBox(-20.0F, -2.0F, -3.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(150, 134).mirror().addBox(-15.0F, -1.0F, 1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 19).mirror().addBox(-8.0F, 2.0F, -7.0F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(60, 70).mirror().addBox(-10.0F, 5.0F, 3.0F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(48, 136).mirror().addBox(-16.0F, 10.0F, -5.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(66, 148).mirror().addBox(-3.0F, 12.0F, -6.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 129).mirror().addBox(-4.0F, 11.0F, 3.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-16.0F, -29.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition ice_stalagmite = right_arm.addOrReplaceChild("ice_stalagmite", CubeListBuilder.create().texOffs(0, 97).mirror().addBox(-2.5F, 0.0F, 0.0F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-19.5436F, -5.999F, 4.0F));

        PartDefinition cube_r6 = ice_stalagmite.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 97).mirror().addBox(-10.5436F, -3.999F, 19.5F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(19.5436F, 3.999F, 8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition ice_stalagmite2 = right_arm.addOrReplaceChild("ice_stalagmite2", CubeListBuilder.create().texOffs(0, 100).mirror().addBox(-2.5F, 0.0F, 0.0F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5436F, -5.999F, 5.0F));

        PartDefinition cube_r7 = ice_stalagmite2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 100).mirror().addBox(-10.5436F, -3.999F, 19.5F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(19.5436F, 3.999F, 8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition ice_stalagmite3 = right_arm.addOrReplaceChild("ice_stalagmite3", CubeListBuilder.create().texOffs(0, 100).mirror().addBox(-2.5F, 0.0F, 0.0F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-14.5436F, -5.999F, -6.0F));

        PartDefinition cube_r8 = ice_stalagmite3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 100).mirror().addBox(-10.5436F, -3.999F, 19.5F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(19.5436F, 3.999F, 8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition right_arm2 = right_arm.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(76, 70).mirror().addBox(-6.5F, 0.0F, -6.0F, 13.0F, 30.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 214).mirror().addBox(-6.5F, 0.0F, -6.0F, 13.0F, 30.0F, 12.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(94, 19).mirror().addBox(-7.5F, -1.0F, -7.0F, 15.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(48, 148).mirror().addBox(3.5F, 10.0F, -8.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 147).mirror().addBox(3.5F, 15.0F, 3.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(150, 128).mirror().addBox(0.5F, 15.0F, -9.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(88, 7).mirror().addBox(0.5F, 11.0F, 6.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(44, 107).mirror().addBox(-7.5F, 12.0F, -7.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(64, 104).mirror().addBox(-8.5F, 17.0F, 4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(59, 96).mirror().addBox(-7.5F, 25.0F, -7.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 22.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition right_finger1 = right_arm2.addOrReplaceChild("right_finger1", CubeListBuilder.create().texOffs(139, 39).mirror().addBox(0.0F, -2.5F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 28.5F, -5.5F));

        PartDefinition right_finger2 = right_arm2.addOrReplaceChild("right_finger2", CubeListBuilder.create().texOffs(138, 0).mirror().addBox(0.0F, 0.0F, -2.5F, 10.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.5F, 30.0F, -3.0F));

        PartDefinition right_finger3 = right_arm2.addOrReplaceChild("right_finger3", CubeListBuilder.create().texOffs(82, 39).mirror().addBox(0.0F, 0.0F, -2.5F, 10.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.5F, 30.0F, 3.0F));

        PartDefinition sunflower_o_4 = right_arm2.addOrReplaceChild("sunflower_o_4", CubeListBuilder.create().texOffs(49, 153).addBox(0.0F, -5.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, 17.0F, -2.0F, -0.5828F, -0.0969F, 0.0115F));

        PartDefinition sunflower_o_5 = right_arm2.addOrReplaceChild("sunflower_o_5", CubeListBuilder.create().texOffs(49, 153).addBox(0.0F, -5.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 9.0F, -8.0F, -1.7962F, -1.3771F, 1.7083F));

        PartDefinition sunflower_o_6 = right_arm2.addOrReplaceChild("sunflower_o_6", CubeListBuilder.create().texOffs(49, 153).addBox(0.0F, -5.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 5.0F, 8.0F, 1.3441F, -1.309F, -1.6309F));

        PartDefinition sunflower_o_7 = right_arm2.addOrReplaceChild("sunflower_o_7", CubeListBuilder.create().texOffs(49, 153).addBox(0.0F, -5.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 22.0F, 7.0F, -2.6085F, -1.3859F, 2.3421F));

        PartDefinition bee_nest2 = right_arm.addOrReplaceChild("bee_nest2", CubeListBuilder.create().texOffs(199, 0).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(169, 0).addBox(-3.5F, 7.0F, -3.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(169, 13).addBox(-4.5F, 2.0F, -4.5F, 9.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.5F, 13.0F, -2.5F));

        PartDefinition sunflower_o_1 = right_arm.addOrReplaceChild("sunflower_o_1", CubeListBuilder.create().texOffs(49, 153).addBox(0.0F, -5.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 12.0F, 5.0F, 0.1239F, 0.3736F, 0.3289F));

        PartDefinition sunflower_o_2 = right_arm.addOrReplaceChild("sunflower_o_2", CubeListBuilder.create().texOffs(49, 153).addBox(1.0F, -5.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, 3.0F, -5.0F, -0.124F, -0.6339F, 0.2586F));

        PartDefinition sunflower_o_3 = right_arm.addOrReplaceChild("sunflower_o_3", CubeListBuilder.create().texOffs(49, 153).addBox(1.0F, -5.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.0F, -6.0F, -2.4654F, -1.0745F, 2.3071F));

        PartDefinition left_leg = total.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(132, 95).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, -4.0F, 0.0F));

        PartDefinition left_leg2 = left_leg.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(0, 96).addBox(-5.0F, 0.0F, -5.0F, 11.0F, 22.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(100, 39).addBox(-6.0F, -1.0F, -6.0F, 13.0F, 10.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 12.0F, -0.5F));

        PartDefinition left_finger_f1 = left_leg2.addOrReplaceChild("left_finger_f1", CubeListBuilder.create().texOffs(0, 42).addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 20.5F, -4.0F, 0.0F, 0.1309F, 0.0F));

        PartDefinition left_finger_f2 = left_leg2.addOrReplaceChild("left_finger_f2", CubeListBuilder.create().texOffs(0, 33).addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 20.5F, -4.0F, 0.0F, -0.1309F, 0.0F));

        PartDefinition left_finger_f3 = left_leg2.addOrReplaceChild("left_finger_f3", CubeListBuilder.create().texOffs(80, 112).addBox(-1.5F, -2.5F, -7.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 20.5F, -5.0F));

        PartDefinition right_leg = total.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(132, 95).mirror().addBox(-3.5F, 0.0F, -3.5F, 7.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.5F, -4.0F, 0.0F));

        PartDefinition right_leg2 = right_leg.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(0, 96).mirror().addBox(-6.0F, 0.0F, -5.0F, 11.0F, 22.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(100, 39).mirror().addBox(-7.0F, -1.0F, -6.0F, 13.0F, 10.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 12.0F, -0.5F));

        PartDefinition right_finger_f1 = right_leg2.addOrReplaceChild("right_finger_f1", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.5F, 20.5F, -4.0F, 0.0F, -0.1309F, 0.0F));

        PartDefinition right_finger_f2 = right_leg2.addOrReplaceChild("right_finger_f2", CubeListBuilder.create().texOffs(0, 33).mirror().addBox(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 20.5F, -4.0F, 0.0F, 0.1309F, 0.0F));

        PartDefinition right_finger_f3 = right_leg2.addOrReplaceChild("right_finger_f3", CubeListBuilder.create().texOffs(80, 112).mirror().addBox(-1.5F, -2.5F, -7.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 20.5F, -5.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(@Nonnull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        this.animate(((TreeEntBase)entity).IDLE_ANIMATION, TreeEntAnimations.IDLE, ageInTicks, 1.0f);
        this.animate(((TreeEntBase)entity).WALK_QUICK_ANIMATION, TreeEntAnimations.WALK_QUICK, ageInTicks, 1.0f);
        this.animate(((TreeEntBase)entity).ATTACK_ANIMATION, TreeEntAnimations.ATTACK, ageInTicks, 1.0f);
        this.animate(((TreeEntBase)entity).WALK_ANIMATION, TreeEntAnimations.WALK, ageInTicks, 1.0f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack,@Nonnull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return tree_ent;
    }

}
