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
        if (ticksLeft > 0) {
            ticksLeft--;
            if (this.targetAnimal == null || this.partner == null || !this.targetAnimal.isAlive() || !this.partner.isAlive()) {
                this.targetAnimal = this.findTarget();
                this.partner = this.findPartner();
                if (this.targetAnimal == null || this.partner == null || !this.targetAnimal.isAlive() || !this.partner.isAlive()) {
                    reset();
                    return;
                }
            }
            if (!this.targetAnimal.isAlive() || !this.partner.isAlive() || this.targetAnimal.getAge() != 0 || this.partner.getAge() != 0) {
                reset();
                return;
            }
            if (ticksLeft <= 0) {
                if (entity.level instanceof ServerWorld) {
                    this.targetAnimal.spawnChildFromBreeding((ServerWorld) entity.level, partner);
                    FeywildMod.getNetwork().sendParticles(entity.level, ParticleSerializer.Type.ANIMAL_BREED, this.entity.getX(), this.entity.getY(), this.entity.getZ(), this.targetAnimal.getX(), this.targetAnimal.getY(), this.targetAnimal.getZ());
                }
                reset();
            } else if (ticksLeft == 110) {
                spellCasting();
            } else if (ticksLeft <= 100) {
                entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetAnimal.position());
                entity.getNavigation().moveTo(targetAnimal, 0.5);
            }
        }
    }

    @Override
    public void start() {
        ticksLeft = 120;
        this.targetAnimal = null;
        this.partner = null;
    }

    private void spellCasting() {
        entity.setCasting(true);
        entity.playSound(ModSoundEvents.pixieSpellcasting, 1, 1);
    }

    protected void reset() {
        entity.setCasting(false);
        targetAnimal = null;
        partner = null;
        ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        return entity.level.random.nextFloat() < 0.01f;
    }

    @Nullable
    private AnimalEntity findTarget() {
        double distance = Double.MAX_VALUE;
        AnimalEntity current = null;
        for (AnimalEntity animal : entity.level.getNearbyEntities(AnimalEntity.class, TARGETING, entity, entity.getBoundingBox().inflate(8))) {
            if (animal.getAge() == 0 && entity.distanceToSqr(animal) < distance) {
                current = animal;
                distance = entity.distanceToSqr(animal);
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
