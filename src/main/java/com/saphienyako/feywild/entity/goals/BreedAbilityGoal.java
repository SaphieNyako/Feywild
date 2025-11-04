package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;


public class BreedAbilityGoal extends Goal {
    private static final TargetingConditions TARGETING = TargetingConditions.forNonCombat().range(8).ignoreLineOfSight();

    private final PixieBase entity;
    private Animal targetAnimal;
    private Animal partner;
    private int ticksLeft = 0;

    private Level level;

    public BreedAbilityGoal(PixieBase entity, Level level) {
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
                if (this.level instanceof ServerLevel) {
                    this.targetAnimal.spawnChildFromBreeding((ServerLevel) this.level, this.partner);
                }
                this.reset();
            } else if (this.ticksLeft == 45) {
                this.spellCasting();
            } else if (this.ticksLeft <= 35) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.targetAnimal.position());
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

        Player owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayer) {
            return this.level.random.nextFloat() < 0.05f;
        } else {
            return false;
        }
    }

    @Nullable
    private Animal findTarget() {
        double distance = Double.MAX_VALUE;
        Animal current = null;
        for (Animal animal : this.level.getNearbyEntities(Animal.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8))) {
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
            for (Animal animal : this.level.getNearbyEntities(Animal.class, TARGETING, this.targetAnimal, this.targetAnimal.getBoundingBox().inflate(8))) {
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
