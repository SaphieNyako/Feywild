package com.saphienyako.feywild.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class LookAtHelper {
    public static void lookAt(LivingEntity target, LivingEntity entity) {

        Vector3d dir = target.position().subtract(entity.position()).normalize();

        float yaw = (float)(MathHelper.atan2(dir.z, dir.x) * (180D / Math.PI)) - 90F;
        float pitch = (float)(-(MathHelper.atan2(dir.y, MathHelper.sqrt(dir.x * dir.x + dir.z * dir.z)) * (180D / Math.PI)));

        entity.yRot = updateRotation(entity.yRot, yaw, 30.0F);
        entity.xRot = updateRotation(entity.xRot, pitch, 30.0F);
        entity.yHeadRot = entity.yRot;
        entity.yBodyRot = entity.yRot;
    }

    private static float updateRotation(float current, float target, float maxChange) {
        float f = MathHelper.wrapDegrees(target - current);
        if (f > maxChange) f = maxChange;
        if (f < -maxChange) f = -maxChange;
        return current + f;
    }

}
