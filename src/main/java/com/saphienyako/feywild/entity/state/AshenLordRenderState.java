package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class AshenLordRenderState extends LivingEntityRenderState {

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState walkingAnimation = new AnimationState();
    public final AnimationState channelAnimation = new AnimationState();
}
