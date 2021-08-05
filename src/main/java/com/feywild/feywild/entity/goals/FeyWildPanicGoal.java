package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.util.FeyEntity;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class FeyWildPanicGoal extends Goal {

    private final FeyEntity entity;
    private final int range;
    private final double speed;

    public FeyWildPanicGoal(FeyEntity entity, double speed, int range) {
        this.entity = entity;
        this.speed = speed;
        this.range = range;
    }
    
    @Override
    public void start() {
        super.start();
        Vector3d targetPos = entity.position();
        if (targetPos.distanceTo(this.entity.position()) < 1.4) {
            for (int i = 0; i < 30; i++) {
                targetPos = new Vector3d(entity.getX() - range + entity.getRandom().nextInt(range * 2), entity.getY() - range + entity.getRandom().nextInt(range * 2), entity.getZ() - range + entity.getRandom().nextInt(range * 2));
                //noinspection deprecation
                if (entity.level.getBlockState(new BlockPos(targetPos.x(), targetPos.y(), targetPos.z())).isAir()) {
                    break;
                }
            }
            this.entity.setDeltaMovement((targetPos.x() - this.entity.getX()) * speed * 10, (targetPos.y() - this.entity.getY()) * speed * 10, (targetPos.z() - this.entity.getZ()) * speed * 10);
            this.entity.lookAt(EntityAnchorArgument.Type.EYES, targetPos);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public boolean canUse() {
        return this.entity.getLastDamageSource() != null && !this.entity.isTamed();
    }

}