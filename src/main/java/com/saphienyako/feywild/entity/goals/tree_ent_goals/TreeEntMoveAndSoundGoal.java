package com.saphienyako.feywild.entity.goals.tree_ent_goals;

import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class TreeEntMoveAndSoundGoal extends WaterAvoidingRandomStrollGoal {
    protected final TreeEntBase entity;

    public TreeEntMoveAndSoundGoal(TreeEntBase entity, double speedModifier) {
        super(entity, speedModifier);
        this.entity = entity;
    }

    @Override
    public void start() {
        //TODO Sound
      //  entity.playSound(ModSoundEvents.treeEntWalking.getSoundEvent(), 0.7f, 1);
        super.start();
    }
}
