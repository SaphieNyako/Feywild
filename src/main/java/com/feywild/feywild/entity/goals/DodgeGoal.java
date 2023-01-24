package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.Titania;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class DodgeGoal extends Goal {

    private final Titania entity;
    private final int range;
    private final double speed;

    public DodgeGoal(Titania entity, double speed, int range) {
        this.entity = entity;
        this.speed = speed;
        this.range = range;
    }

    @Override
    public void start() {
        super.start();
        Vec3 targetPos = this.entity.position();
        if (targetPos.distanceTo(this.entity.position()) < 1.4) {
            for (int i = 0; i < 30; i++) {
                targetPos = new Vec3(this.entity.getX() - this.range + this.entity.getRandom().nextInt(this.range * 2), this.entity.getY() - this.range + this.entity.getRandom().nextInt(this.range * 2), this.entity.getZ() - this.range + this.entity.getRandom().nextInt(this.range * 2));
                if (this.entity.level.getBlockState(new BlockPos(targetPos.x(), targetPos.y(), targetPos.z())).isAir()) {
                    break;
                }
            }
            this.entity.setDeltaMovement((targetPos.x() - this.entity.getX()) * this.speed * 10, (targetPos.y() - this.entity.getY()) * this.speed * 10, (targetPos.z() - this.entity.getZ()) * this.speed * 10);
            this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, targetPos);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public boolean canUse() {
        return this.entity.getLastDamageSource() != null;
    }

}
