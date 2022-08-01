package com.feywild.feywild.entity.goals;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class TargetBreedGoal extends Goal {

    private static final TargetingConditions TARGETING = TargetingConditions.forNonCombat().range(8).ignoreLineOfSight();

    private final Fey entity;
    private Animal targetAnimal;
    private Animal partner;
    private int ticksLeft = 0;

    public TargetBreedGoal(Fey entity) {
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
                if (this.entity.level instanceof ServerLevel) {
                    this.targetAnimal.spawnChildFromBreeding((ServerLevel) this.entity.level, this.partner);
                    FeywildMod.getNetwork().sendParticles(this.entity.level, ParticleSerializer.Type.ANIMAL_BREED, this.entity.getX(), this.entity.getY(), this.entity.getZ(), this.targetAnimal.getX(), this.targetAnimal.getY(), this.targetAnimal.getZ());
                }
                this.reset();
            } else if (this.ticksLeft == 65) {
                this.spellCasting();
            } else if (this.ticksLeft <= 55) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.targetAnimal.position());
                this.entity.getNavigation().moveTo(this.targetAnimal, 0.5);
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 75;
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
        Player owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayer serverPlayer) {
            return this.entity.level.random.nextFloat() < 0.01f && QuestData.get(serverPlayer).getAlignment() == entity.alignment;
        } else {
            return false;
        }
    }

    @Nullable
    private Animal findTarget() {
        double distance = Double.MAX_VALUE;
        Animal current = null;
        for (Animal animal : this.entity.level.getNearbyEntities(Animal.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8))) {
            if (animal.getAge() == 0 && this.entity.distanceToSqr(animal) < distance) {
                current = animal;
                distance = this.entity.distanceToSqr(animal);
            }
        }
        return current;
    }

    @Nullable
    private Animal findPartner() {
        if (this.targetAnimal != null) {
            double distance = Double.MAX_VALUE;
            Animal current = null;
            for (Animal animal : this.targetAnimal.level.getNearbyEntities(Animal.class, TARGETING, this.targetAnimal, this.targetAnimal.getBoundingBox().inflate(8))) {
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
