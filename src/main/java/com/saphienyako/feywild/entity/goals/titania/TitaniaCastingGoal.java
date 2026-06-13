package com.saphienyako.feywild.entity.goals.titania;

import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.SpriteEntity;
import com.saphienyako.feywild.entity.TitaniaEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class TitaniaCastingGoal extends Goal {

    private final TitaniaEntity mob;
    private int castTime;

    private int cooldown = 0;
    public TitaniaCastingGoal(TitaniaEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return cooldown <= 0 && mob.getTarget() != null && mob.getTarget().isAlive();
    }

    @Override
    public void start() {
        castTime = 20;
        mob.setState(TitaniaEntity.State.CASTING);
    }

    @Override
    public void tick() {
        if (cooldown > 0) {
            cooldown--;
            return;
        }
        LivingEntity target = mob.getTarget();

        if (target == null || !target.isAlive()) {
            stop();
            return;
        }

        mob.getLookControl().setLookAt(target);

        if (castTime-- > 0) return;

        if (!mob.level().isClientSide) {
            castSpriteProjectile(target);
        }

        stop();
    }

    private void castSpriteProjectile(LivingEntity target) {
        SpriteEntity sprite = new SpriteEntity(ModEntities.SPRITE.get(), mob.level());

        sprite.moveTo(mob.getX(), mob.getY() + 2, mob.getZ());

        SpriteEntity.SpriteVariant variant = SpriteEntity.SpriteVariant.values()[mob.getRandom().nextInt(SpriteEntity.SpriteVariant.values().length)];

        sprite.setVariant(variant);
        sprite.setMode(SpriteEntity.Mode.PROJECTILE);

        Vec3 targetPos = target.position();

        sprite.setDeltaMovement(
                targetPos.subtract(sprite.position()).normalize().scale(0.6)
        );

        mob.level().addFreshEntity(sprite);
    }

    @Override
    public void stop() {
        mob.setState(TitaniaEntity.State.IDLE_FLYING);
        cooldown = 60; // 3 seconds pause between casts
    }

    @Override
    public boolean canContinueToUse() {
        return mob.getTarget() != null && mob.getTarget().isAlive();
    }
}
