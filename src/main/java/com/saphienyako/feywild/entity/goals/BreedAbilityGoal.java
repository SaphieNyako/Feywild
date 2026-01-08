package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


import javax.annotation.Nullable;


public class BreedAbilityGoal extends Goal {
    private final PixieBase entity;
    private AnimalEntity targetAnimal;
    private AnimalEntity partner;
    private int ticksLeft = 0;

    private World level;

    public BreedAbilityGoal(PixieBase entity, World level) {
        this.entity = entity;
        this.level = level;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;
            if (this.targetAnimal == null || this.partner == null || !this.targetAnimal.isAlive() || !this.partner.isAlive()) {
                this.targetAnimal = this.findTarget();
                this.partner = this.findPartner();
                if (this.targetAnimal == null || this.partner == null || !this.targetAnimal.isAlive() || !this.partner.isAlive()) {
                    this.reset();
                    return;
                }
            }
            if (!this.targetAnimal.isAlive() || !this.partner.isAlive() || this.targetAnimal.getAge() != 0 || this.partner.getAge() != 0) {
                this.reset();
                return;
            }
            if (this.ticksLeft <= 0) {
                if (this.level instanceof ServerWorld) {
                    this.targetAnimal.spawnChildFromBreeding((ServerWorld) this.level, this.partner);
                }
                this.reset();
            } else if (this.ticksLeft == 45) {
                this.spellCasting();
            } else if (this.ticksLeft <= 35) {
                LookAtHelper.lookAt(this.targetAnimal, this.entity);
                this.entity.getNavigation().moveTo(this.targetAnimal, 0.5);
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 55;
        this.targetAnimal = null;
        this.partner = null;
    }

    private void spellCasting() {
        this.entity.setState(PixieBase.State.SPELL_CASTING);
        this.entity.playSound(ModSounds.PIXIE_SPELL_CASTING.get(), 0.7f, 1);
    }

    protected void reset() {
        this.entity.setState(PixieBase.State.IDLE);
        this.targetAnimal = null;
        this.partner = null;
        this.ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        PlayerEntity owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayerEntity && this.entity.getAbilityActive()) {
            return this.level.random.nextFloat() < 0.02f;
        } else {
            return false;
        }
    }

    @Nullable
    private AnimalEntity findTarget() {
        double distance = Double.MAX_VALUE;
        AnimalEntity current = null;
        for (AnimalEntity animal : this.level.getEntitiesOfClass(AnimalEntity.class, this.entity.getBoundingBox().inflate(8))) {
            double dist = this.entity.distanceToSqr(animal);
            if (dist < distance) {
                current = animal;
                distance = dist;
            }
        }
        return current;
    }

    @Nullable
    private AnimalEntity findPartner() {
        if (this.targetAnimal != null) {
            double distance = Double.MAX_VALUE;
            AnimalEntity current = null;
            for (AnimalEntity animal : this.level.getEntitiesOfClass(AnimalEntity.class, this.entity.getBoundingBox().inflate(8))) {
                if (animal.getAge() == 0) {
                    // We need to set both entities in love to get correct results
                    // from canMate. So we store the old love time to set it back later
                    int oldInLove1 = this.targetAnimal.getInLoveTime();
                    int oldInLove2 = animal.getInLoveTime();
                    this.targetAnimal.setInLoveTime(1);
                    animal.setInLoveTime(1);
                    if (this.targetAnimal.canMate(animal) && this.targetAnimal.distanceToSqr(animal) < distance) {
                        current = animal;
                        distance = this.targetAnimal.distanceToSqr(animal);
                    }
                    this.targetAnimal.setInLoveTime(oldInLove1);
                    animal.setInLoveTime(oldInLove2);
                }
            }
            return current;
        }
        return null;
    }
}
