package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class MandragoraRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState singAnimation = new AnimationState();
    public final AnimationState poseAnimation = new AnimationState();
    public final AnimationState walkAnimation = new AnimationState();

    public String variantName = "default";
}
