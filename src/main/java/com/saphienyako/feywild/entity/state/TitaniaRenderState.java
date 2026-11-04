package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class TitaniaRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState flyingAnimation = new AnimationState();
    public final AnimationState castingAnimation = new AnimationState();
    public final AnimationState enchantingAnimation = new AnimationState();
}
