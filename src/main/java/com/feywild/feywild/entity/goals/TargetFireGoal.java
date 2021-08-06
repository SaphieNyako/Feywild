package com.feywild.feywild.entity.goals;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;

public class TargetFireGoal extends Goal {

    private static final EntityPredicate TARGETING = (new EntityPredicate()).range(8.0D).allowInvulnerable().allowSameTeam().allowUnseeable();

    private final FeyEntity entity;
    private MonsterEntity targetMonster;
    private int ticksLeft = 0;

    public TargetFireGoal(FeyEntity entity) {
        this.entity = entity;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            ticksLeft--;
            if (this.targetMonster == null) {
                this.targetMonster = findTarget();
                if (targetMonster == null) {
                    this.reset();
                    return;
                }
            }
            if (ticksLeft <= 0) {
                this.targetMonster.setSecondsOnFire(120);
                FeywildMod.getNetwork().sendParticles(entity.level, ParticleSerializer.Type.MONSTER_FIRE, this.entity.getX(), this.entity.getY(), this.entity.getZ(), this.targetMonster.getX(), this.targetMonster.getY(), this.targetMonster.getZ());
                reset();
            } else if (ticksLeft == 110) {
                spellCasting();
            }
        }
    }

    private void spellCasting() {
        entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetMonster.position());
        entity.setCasting(true);
        entity.playSound(ModSoundEvents.pixieSpellcasting, 1, 1);
    }

    @Override
    public void start() {
        this.ticksLeft = 120;
        this.targetMonster = null;
    }

    protected void reset() {
        entity.setCasting(false);
        targetMonster = null;
        ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        return entity.isTamed() && entity.level.random.nextFloat() < 0.005f;
    }

    private MonsterEntity findTarget() {
        double distance = Double.MAX_VALUE;
        MonsterEntity current = null;
        for (MonsterEntity monster : entity.level.getNearbyEntities(MonsterEntity.class, TARGETING, entity, entity.getBoundingBox().inflate(8))) {
            if (entity.distanceToSqr(monster) < distance && !monster.isOnFire()) {
                current = monster;
                distance = entity.distanceToSqr(monster);
            }
        }
        return current;
    }
}
