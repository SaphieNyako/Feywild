package com.feywild.feywild.entity.goals;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.ShroomlingEntity;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SneezeGoal extends Goal {

    private static final EntityPredicate TARGETING = (new EntityPredicate()).allowNonAttackable().range(4).allowUnseeable();

    protected final World level;
    protected ShroomlingEntity entity;
    private int ticksLeft = 0;
    private AnimalEntity targetAnimal;

    public SneezeGoal(ShroomlingEntity shroomling) {
        this.entity = shroomling;
        this.level = shroomling.level;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;

            if (this.ticksLeft <= 0) {

                if (this.targetAnimal == null || !this.targetAnimal.isAlive()) {
                    this.targetAnimal = this.findTarget();
                    if (this.targetAnimal != null && this.targetAnimal.isAlive()) {
                        MooshroomEntity cow = new MooshroomEntity(EntityType.MOOSHROOM, this.level);

                        cow.setPos(this.targetAnimal.getX(), this.targetAnimal.getY(), this.targetAnimal.getZ());
                        this.targetAnimal.remove();

                        level.addFreshEntity(cow);
                        cow.playSound(SoundEvents.PANDA_SNEEZE, 1, 0.6f);
                        FeywildMod.getNetwork().sendParticles(this.entity.level, ParticleSerializer.Type.SHROOMLING_SNEEZE, cow.getX(), cow.getY(), cow.getZ());
                    }
                }
                if (this.entity.isTamed()) {
                    if (level.getBrightness(LightType.BLOCK, this.entity.blockPosition()) < 3
                            && this.entity.level.getBlockState(this.entity.blockPosition()).canBeReplaced(Fluids.WATER)) {

                        this.level.setBlock(this.entity.blockPosition(), ModBlocks.feyMushroom.defaultBlockState(), 2);
                    }
                }

                this.reset();

            } else if (this.ticksLeft == 45) {
                FeywildMod.getNetwork().sendParticles(this.entity.level, ParticleSerializer.Type.SHROOMLING_SNEEZE, this.entity.getX(), this.entity.getY(), this.entity.getZ());
            } else if (this.ticksLeft == 90) {
                this.sneezing();
                this.entity.playSound(ModSoundEvents.shroomlingSneeze, 1, 1);
            }
        }
    }

    private void reset() {
        this.entity.setState(ShroomlingEntity.State.IDLE);
        this.targetAnimal = null;
        this.ticksLeft = -1;
    }

    private void sneezing() {this.entity.setState(ShroomlingEntity.State.SNEEZING);}

    @Override
    public void start() {
        this.ticksLeft = 100;
        this.targetAnimal = null;
    }

    @Override
    public boolean canUse() {
        return level.random.nextFloat() < 0.003f
                && !(this.entity.getState() == ShroomlingEntity.State.WAVING);
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0
                && !(this.entity.getState() == ShroomlingEntity.State.WAVING);
    }

    @Nullable
    private AnimalEntity findTarget() {
        double distance = Double.MAX_VALUE;
        AnimalEntity current = null;
        for (AnimalEntity animal : this.entity.level.getNearbyEntities(CowEntity.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8))) {
            if (animal.getAge() == 0 && this.entity.distanceToSqr(animal) < distance) {
                current = animal;
                distance = this.entity.distanceToSqr(animal);
            }
        }
        return current;
    }

}
