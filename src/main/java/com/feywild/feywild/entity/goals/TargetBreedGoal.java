package com.feywild.feywild.entity.goals;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class TargetBreedGoal extends Goal {

    private static final EntityPredicate TARGETING = (new EntityPredicate()).range(8).allowInvulnerable().allowSameTeam().allowUnseeable();

    private final FeyEntity entity;
    private AnimalEntity targetAnimal;
    private AnimalEntity partner;
    private int ticksLeft = 0;

    public TargetBreedGoal(FeyEntity entity) {
        this.entity = entity;
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
                if (this.entity.level instanceof ServerWorld) {
                    this.targetAnimal.spawnChildFromBreeding((ServerWorld) this.entity.level, this.partner);
                    FeywildMod.getNetwork().sendParticles(this.entity.level, ParticleSerializer.Type.ANIMAL_BREED, this.entity.getX(), this.entity.getY(), this.entity.getZ(), this.targetAnimal.getX(), this.targetAnimal.getY(), this.targetAnimal.getZ());
                }
                this.reset();
            } else if (this.ticksLeft == 110) {
                this.spellCasting();
            } else if (this.ticksLeft <= 100) {
                this.entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetAnimal.position());
                this.entity.getNavigation().moveTo(this.targetAnimal, 0.5);
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 120;
        this.targetAnimal = null;
        this.partner = null;
    }

    private void spellCasting() {
        this.entity.setCasting(true);
        this.entity.playSound(ModSoundEvents.pixieSpellcasting, 1, 1);
    }

    protected void reset() {
        this.entity.setCasting(false);
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
        return this.entity.isTamed() && this.entity.level.random.nextFloat() < 0.01f;
    }

    @Nullable
    private AnimalEntity findTarget() {
        double distance = Double.MAX_VALUE;
        AnimalEntity current = null;
        for (AnimalEntity animal : this.entity.level.getNearbyEntities(AnimalEntity.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8))) {
            if (animal.getAge() == 0 && this.entity.distanceToSqr(animal) < distance) {
                current = animal;
                distance = this.entity.distanceToSqr(animal);
            }
        }
        return current;
    }

    @Nullable
    private AnimalEntity findPartner() {
        if (this.targetAnimal != null) {
            double distance = Double.MAX_VALUE;
            AnimalEntity current = null;
            for (AnimalEntity animal : this.targetAnimal.level.getNearbyEntities(AnimalEntity.class, TARGETING, this.targetAnimal, this.targetAnimal.getBoundingBox().inflate(8))) {
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
