package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.base.FeyBase;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class GroundIronPanicGoal extends Goal {

    private static final TargetingConditions TARGETING = TargetingConditions.forNonCombat().range(8).ignoreLineOfSight();

    private final LivingEntity entity;
    private final Level level;
    private final double speed;
    private final double range;
    private Vec3 panicDirection = Vec3.ZERO;
    private int panicTicks;

    public GroundIronPanicGoal(FeyBase entity, Level level, double speed, double range) {
        this.entity = entity;
        this.level = level;
        this.speed = speed;
        this.range = range;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return findPlayer() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return panicTicks > 0;
    }

    @Override
    public void start() {
        panicTicks = 40;

        Player targetPlayer = findPlayer();
        if (targetPlayer == null) return;

        Vec3 direction = entity.position().subtract(targetPlayer.position());
        panicDirection = new Vec3(direction.x, 0, direction.z).normalize();
    }

    @Override
    public void tick() {
        panicTicks--;

        if (panicDirection == Vec3.ZERO) return;
        //stick to ground pls T_T
        Vec3 motion = new Vec3(
                panicDirection.x * speed,
                entity.getDeltaMovement().y,
                panicDirection.z * speed
        );

        entity.setDeltaMovement(motion);

        float yaw = (float) (Math.atan2(panicDirection.z, panicDirection.x) * (180.0 / Math.PI)) - 90f;
        entity.setYRot(yaw);
        entity.yRotO = yaw;
        entity.setYHeadRot(yaw);
        entity.yHeadRotO = yaw;
    }

    @Override
    public void stop() {
        entity.setDeltaMovement(Vec3.ZERO);
        panicDirection = Vec3.ZERO;
    }

    @Nullable
    protected Player findPlayer() {
        double closest = Double.MAX_VALUE;
        Player result = null;

        for (Player player : level.getNearbyEntities(Player.class, TARGETING, entity, entity.getBoundingBox().inflate(range))) {
            if (!player.isCreative() && isHoldingIron(player)) {
                double distance = entity.distanceToSqr(player);
                if (distance < closest) {
                    closest = distance;
                    result = player;
                }
            }
        }
        return result;
    }

    private boolean isHoldingIron(Player player) {
        return isIron(player.getMainHandItem()) || isIron(player.getOffhandItem());
    }

    private boolean isIron(ItemStack stack) {
        return !stack.isEmpty() && stack.is(Items.IRON_INGOT);
    }
}