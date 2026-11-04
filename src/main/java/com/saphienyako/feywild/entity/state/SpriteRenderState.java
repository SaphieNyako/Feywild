package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class SpriteRenderState extends LivingEntityRenderState {
    public final AnimationState flyAnimation = new AnimationState();
    public final AnimationState flyIdleAnimation = new AnimationState();
    public final AnimationState happyAnimation = new AnimationState();
    public final AnimationState angryAnimation = new AnimationState();

    public String variantName = "default";
}
