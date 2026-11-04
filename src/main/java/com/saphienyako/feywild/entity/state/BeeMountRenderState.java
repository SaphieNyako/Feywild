package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;

public class BeeMountRenderState extends LivingEntityRenderState {
    public final AnimationState flyAnimation = new AnimationState();
    public final AnimationState flyIdleAnimation = new AnimationState();

    public boolean isAngry;

    public Identifier armorTexture;
}
