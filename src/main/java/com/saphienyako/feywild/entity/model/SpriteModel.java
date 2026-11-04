package com.saphienyako.feywild.entity.model;

import com.saphienyako.feywild.entity.animations.SpriteAnimations;
import com.saphienyako.feywild.entity.state.SpriteRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SpriteModel extends EntityModel<@NotNull SpriteRenderState> {
    //Model design is based on the Botania Pixies, asked permission to use.
    private final KeyframeAnimation flyAnimation;
    private final KeyframeAnimation flyIdleAnimation;
    private final KeyframeAnimation happyAnimation;
    private final KeyframeAnimation angryAnimation;

    private final ModelPart sprite;
    private final ModelPart body;
    private final ModelPart left_wings;
    private final ModelPart left_wing1;
    private final ModelPart left_wing2;
    private final ModelPart right_wings;
    private final ModelPart right_wing1;
    private final ModelPart right_wing2;

    public SpriteModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);

        this.flyAnimation = SpriteAnimations.FLY.bake(root);
        this.flyIdleAnimation = SpriteAnimations.FLY_IDLE.bake(root);
        this.happyAnimation = SpriteAnimations.HAPPY.bake(root);
        this.angryAnimation = SpriteAnimations.ANGRY.bake(root);

        this.sprite = root.getChild("sprite");
        this.body = this.sprite.getChild("body");
        this.left_wings = this.body.getChild("left_wings");
        this.left_wing1 = this.left_wings.getChild("left_wing1");
        this.left_wing2 = this.left_wings.getChild("left_wing2");
        this.right_wings = this.body.getChild("right_wings");
        this.right_wing1 = this.right_wings.getChild("right_wing1");
        this.right_wing2 = this.right_wings.getChild("right_wing2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition sprite = partdefinition.addOrReplaceChild("sprite", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = sprite.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition left_wings = body.addOrReplaceChild("left_wings", CubeListBuilder.create(), PartPose.offset(2.0F, 0.0F, 0.0F));

        PartDefinition left_wing1 = left_wings.addOrReplaceChild("left_wing1", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -4.5F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition left_wing2 = left_wings.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, -1.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition right_wings = body.addOrReplaceChild("right_wings", CubeListBuilder.create(), PartPose.offset(-2.0F, 0.0F, 0.0F));

        PartDefinition right_wing1 = right_wings.addOrReplaceChild("right_wing1", CubeListBuilder.create().texOffs(0, 8).mirror().addBox(-7.0F, -4.5F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition right_wing2 = right_wings.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-4.0F, -1.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(@NotNull SpriteRenderState state) {
        super.setupAnim(state);
        this.applyHeadRotation(state.yRot, state.xRot, state.ageInTicks);

        this.flyAnimation.apply(state.flyAnimation, state.ageInTicks, 2.0F);
        this.flyIdleAnimation.apply(state.flyIdleAnimation, state.ageInTicks, 1.0F);
        this.happyAnimation.apply(state.happyAnimation, state.ageInTicks, 1.0F);
        this.angryAnimation.apply(state.angryAnimation, state.ageInTicks, 1.0F);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);
        //TODO instead of head, body?
        this.body.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.body.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }
}
