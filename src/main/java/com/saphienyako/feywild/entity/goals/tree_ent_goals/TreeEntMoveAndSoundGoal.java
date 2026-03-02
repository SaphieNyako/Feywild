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
        entity.playSound(entity.getWalkingSound(), 0.1f, 1);
        super.start();
    }
}
