package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class ShroomlingRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState sneezeAnimation = new AnimationState();
    public final AnimationState waveAnimation = new AnimationState();
    public final AnimationState poseAnimation = new AnimationState();
    public final AnimationState walkAnimation = new AnimationState();

    public String variantName = "default";
}
