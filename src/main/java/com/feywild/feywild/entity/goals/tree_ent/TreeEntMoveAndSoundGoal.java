package com.feywild.feywild.entity.goals.tree_ent;

import com.feywild.feywild.entity.base.TreeEnt;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class TreeEntMoveAndSoundGoal extends WaterAvoidingRandomStrollGoal {

    protected final TreeEnt entity;

    public TreeEntMoveAndSoundGoal(TreeEnt entity, double speedModifier) {
        super(entity, speedModifier);
        this.entity = entity;
    }

    @Override
    public void start() {
        entity.playSound(ModSoundEvents.treeEntWalking.getSoundEvent(), 0.7f, 1);
        super.start();
    }
}
