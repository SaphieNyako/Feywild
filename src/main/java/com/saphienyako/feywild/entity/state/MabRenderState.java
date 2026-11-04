package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class MabRenderState extends LivingEntityRenderState {
    public final AnimationState flyingIdleAnimation = new AnimationState();
    public final AnimationState flyingAnimation = new AnimationState();
    public final AnimationState channelAnimation = new AnimationState();
    public final AnimationState intimidationAnimation = new AnimationState();
    public final AnimationState attackingAnimation = new AnimationState();
}
