package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.base.PixieBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;


import javax.annotation.Nullable;
import java.util.List;


public class IronPanicGoal extends Goal {

    private final LivingEntity entity;
    private final double range;
    private World level;

    public IronPanicGoal(PixieBase entity, World level, double range) {
        this.entity = entity;
        this.level = level;
        this.range = range;
    }

    @Override
    public boolean canUse() {
        List<PlayerEntity> players = level.getEntitiesOfClass(PlayerEntity.class,
                entity.getBoundingBox().inflate(range),
                player -> !player.isCreative() && isHoldingIron(player));

        return !players.isEmpty();
    }

    @Override
    public void start() {
        PlayerEntity targetPlayer = findPlayer();

        if (targetPlayer == null) return;
        double dx = entity.getX() - targetPlayer.getX();
        double dy = entity.getY() - targetPlayer.getY();
        double dz = entity.getZ() - targetPlayer.getZ();
        Vector3d dir = new Vector3d(dx, dy, dz).normalize();

        double intensity = 0.4;
        this.entity.setDeltaMovement(dir.scale(intensity));


        LookAtHelper.lookAt(targetPlayer, entity);
    }
    /*
    private void lookAt(PlayerEntity target) {

        Vector3d dir = target.position().subtract(entity.position()).normalize();

        float yaw = (float)(MathHelper.atan2(dir.z, dir.x) * (180D / Math.PI)) - 90F;
        float pitch = (float)(-(MathHelper.atan2(dir.y, MathHelper.sqrt(dir.x * dir.x + dir.z * dir.z)) * (180D / Math.PI)));

        entity.yRot = updateRotation(entity.yRot, yaw, 30.0F);
        entity.xRot = updateRotation(entity.xRot, pitch, 30.0F);
        entity.yHeadRot = entity.yRot;
        entity.yBodyRot = entity.yRot;
    }

    private float updateRotation(float current, float target, float maxChange) {
        float f = MathHelper.wrapDegrees(target - current);
        if (f > maxChange) f = maxChange;
        if (f < -maxChange) f = -maxChange;
        return current + f;
    }

     */
    private boolean isHoldingIron(PlayerEntity player) {
       ItemStack main = player.getMainHandItem();
       ItemStack off = player.getOffhandItem();

        return isIron(main) || isIron(off);
    }

    private boolean isIron(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.IRON_INGOT;
    }

    @Nullable
    private PlayerEntity findPlayer() {
        double distance = Double.MAX_VALUE;
        PlayerEntity current = null;
        AxisAlignedBB box = this.entity.getBoundingBox().inflate(range);

        List<PlayerEntity> players = level.getEntitiesOfClass(PlayerEntity.class, box,
                player -> !player.isCreative() && isHoldingIron(player));

        for (PlayerEntity player : players) {
            double distSq = this.entity.distanceToSqr(player);
            if (distSq < distance) {
                current = player;
                distance = distSq;
            }
        }
        return current;
    }
}


