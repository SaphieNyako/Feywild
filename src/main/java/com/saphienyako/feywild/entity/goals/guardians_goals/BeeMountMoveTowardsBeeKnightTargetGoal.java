package com.saphienyako.feywild.entity.goals.guardians_goals;

import com.saphienyako.feywild.entity.BeeKnightEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BeeMountMoveTowardsBeeKnightTargetGoal extends MoveTowardsTargetGoal {

    private final PathfinderMob mob;

    private LivingEntity target;
    private double wantedX;
    private double wantedY;
    private double wantedZ;

    private final PathfinderMob mount;
    private final double speedModifier;
    private final float within;
    public BeeMountMoveTowardsBeeKnightTargetGoal(BeeKnightEntity bee_knight, double speedModifier, float range) {
        super(bee_knight, speedModifier, range);
        this.mob = bee_knight;
        this.mount = bee_knight.getMount();
        this.speedModifier = speedModifier;
        this.within = range;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        this.target = this.mob.getTarget();
        if (this.target == null) {
            return false;
        } else if (this.target.distanceToSqr(this.mob) > (double)(this.within * this.within)) {
            return false;
        } else {
            Vec3 vec3 = DefaultRandomPos.getPosTowards(this.mob, 16, 7, this.target.position(), (float) (Math.PI / 2));
            if (vec3 == null) {
                return false;
            } else {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                return true;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.target.isAlive() && this.target.distanceToSqr(this.mob) < (double)(this.within * this.within);
    }

    @Override
    public void stop() {
        this.target = null;
    }

    @Override
    public void start() {
        if(this.mount != null) {
            this.mount.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        }
    }

}
