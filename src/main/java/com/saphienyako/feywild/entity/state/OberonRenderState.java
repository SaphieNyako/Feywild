package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class OberonRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState walkingAnimation = new AnimationState();
    public final AnimationState chargingAnimation = new AnimationState();
    public final AnimationState kickingAnimation = new AnimationState();
    public final AnimationState rearingAnimation = new AnimationState();
}
