package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.base.PixieEntity;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class FeyWildPanicGoal extends Goal {

    private final PixieEntity entity;
    private final int range;
    private final double speed;

    public FeyWildPanicGoal(PixieEntity entity, double speed, int range) {
        this.entity = entity;
        this.speed = speed;
        this.range = range;
    }
    
    @Override
    public void start() {
        super.start();
        Vector3d targetPos = this.entity.position();
        if (targetPos.distanceTo(this.entity.position()) < 1.4) {
            for (int i = 0; i < 30; i++) {
                targetPos = new Vector3d(this.entity.getX() - this.range + this.entity.getRandom().nextInt(this.range * 2), this.entity.getY() - this.range + this.entity.getRandom().nextInt(this.range * 2), this.entity.getZ() - this.range + this.entity.getRandom().nextInt(this.range * 2));
                //noinspection deprecation
                if (this.entity.level.getBlockState(new BlockPos(targetPos.x(), targetPos.y(), targetPos.z())).isAir()) {
                    break;
                }
            }
            this.entity.setDeltaMovement((targetPos.x() - this.entity.getX()) * this.speed * 10, (targetPos.y() - this.entity.getY()) * this.speed * 10, (targetPos.z() - this.entity.getZ()) * this.speed * 10);
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