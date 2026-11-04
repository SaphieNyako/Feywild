package com.saphienyako.feywild.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemStack;

public class BeeKnightRenderState extends LivingEntityRenderState {
    public final AnimationState sitAnimation = new AnimationState();
    public final AnimationState attackAnimation = new AnimationState();
    public final AnimationState wingAnimation = new AnimationState();

    public boolean isAttacking;

    public Identifier armorTexture;
    public ItemStack spear = ItemStack.EMPTY;
}
