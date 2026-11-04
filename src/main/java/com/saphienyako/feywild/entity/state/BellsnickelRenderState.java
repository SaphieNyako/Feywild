package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class BellsnickelRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState tradeAnimation = new AnimationState();
    public final AnimationState poseAnimation = new AnimationState();
    public final AnimationState walkAnimation = new AnimationState();

    public String variantName = "default";
}
