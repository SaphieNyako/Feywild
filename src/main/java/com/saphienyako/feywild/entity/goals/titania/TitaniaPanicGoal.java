package com.saphienyako.feywild.entity.goals.titania;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class TitaniaPanicGoal extends Goal {

    private final LivingEntity entity;
    private final int range;
    private final double speed;
    private int panicTime;
    private long lastHurtTimestamp;

    public TitaniaPanicGoal(LivingEntity entity, double speed, int range) {
        this.entity = entity;
        this.speed = speed;
        this.range = range;
    }

    @Override
    public void tick() {
        panicTime--;
    }

    @Override
    public void start() {

        panicTime = 20;

        Vec3 targetPos = null;

        for (int i = 0; i < 30; i++) {

            Vec3 candidate = new Vec3(
                    entity.getX() - range + entity.getRandom().nextInt(range * 2),
                    entity.getY() - range + entity.getRandom().nextInt(range * 2),
                    entity.getZ() - range + entity.getRandom().nextInt(range * 2)
            );

            Vec3 offset = candidate.subtract(entity.position());

            if (entity.level().noCollision(entity, entity.getBoundingBox().move(offset))) {
                targetPos = candidate;
                break;
            }
        }

        if (targetPos != null) {

            entity.setDeltaMovement(
                    (targetPos.x() - entity.getX()) * speed * 20,
                    (targetPos.y() - entity.getY()) * speed * 20,
                    (targetPos.z() - entity.getZ()) * speed * 20
            );

            entity.lookAt(EntityAnchorArgument.Anchor.EYES, targetPos);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return panicTime > 0;
    }

    @Override
    public boolean canUse() {

        if (entity.hurtTime > 0 &&
                entity.tickCount != lastHurtTimestamp) {

            lastHurtTimestamp = entity.tickCount;
            return true;
        }

        return false;
    }

    @Override
    public void stop() {
        entity.setDeltaMovement(Vec3.ZERO);
    }
}
