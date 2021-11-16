package com.feywild.feywild.entity.goals;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Objects;

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
            this.ticksLeft--;
            if (this.targetMonster == null) {
                this.targetMonster = this.findTarget();
                if (this.targetMonster == null) {
                    this.reset();
                    return;
                }
            }
            if (this.ticksLeft <= 0) {
                this.targetMonster.setSecondsOnFire(120);
                FeywildMod.getNetwork().sendParticles(this.entity.level, ParticleSerializer.Type.MONSTER_FIRE, this.entity.getX(), this.entity.getY(), this.entity.getZ(), this.targetMonster.getX(), this.targetMonster.getY(), this.targetMonster.getZ());
                this.reset();
            } else if (this.ticksLeft == 110) {
                this.spellCasting();
            }
        }
    }

    private void spellCasting() {
        this.entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetMonster.position());
        this.entity.setCasting(true);
        this.entity.playSound(ModSoundEvents.pixieSpellcasting, 1, 1);
    }

    @Override
    public void start() {
        this.ticksLeft = 120;
        this.targetMonster = null;
    }

    protected void reset() {
        this.entity.setCasting(false);
        this.targetMonster = null;
        this.ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        return this.entity.isTamed() && this.entity.level.random.nextFloat() < 0.005f  && QuestData.get((ServerPlayerEntity) Objects.requireNonNull(entity.getOwner())).getAlignment() == entity.alignment;
    }

    private MonsterEntity findTarget() {
        double distance = Double.MAX_VALUE;
        MonsterEntity current = null;
        for (MonsterEntity monster : this.entity.level.getNearbyEntities(MonsterEntity.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8))) {
            if (this.entity.distanceToSqr(monster) < distance && !monster.isOnFire()) {
                current = monster;
                distance = this.entity.distanceToSqr(monster);
            }
        }
        return current;
    }
}
