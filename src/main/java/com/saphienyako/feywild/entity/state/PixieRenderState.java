package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class PixieRenderState extends LivingEntityRenderState {

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState spellCastingAnimation = new AnimationState();
}
