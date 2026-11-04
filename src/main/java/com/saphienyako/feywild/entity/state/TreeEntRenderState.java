package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class TreeEntRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState walkQuickAnimation = new AnimationState();
    public final AnimationState attackAnimation = new AnimationState();
    public final AnimationState walkAnimation = new AnimationState();

    public String alignmentName = "default";
}
