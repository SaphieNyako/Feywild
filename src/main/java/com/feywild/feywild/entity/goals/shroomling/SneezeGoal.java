package com.feywild.feywild.entity.goals.shroomling;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.Shroomling;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

public class SneezeGoal extends Goal {

    private static final TargetingConditions TARGETING = TargetingConditions.forNonCombat().range(4).ignoreLineOfSight();

    protected final Level level;
    protected final Shroomling entity;
    private int ticksLeft = 0;
    private Animal targetAnimal;
    private Player targetPlayer;

    public SneezeGoal(Shroomling shroomling) {
        this.entity = shroomling;
        this.level = shroomling.level();

    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;
            if (this.ticksLeft <= 0) {
                if (this.entity.isTamed()) {
                    // CHANGE COWS INTO MOOSHROOMS
                    if (this.targetAnimal == null || !this.targetAnimal.isAlive()) {
                        this.targetAnimal = this.findCow();
                        if (this.targetAnimal != null && this.targetAnimal.isAlive()) {
                            MushroomCow cow = new MushroomCow(EntityType.MOOSHROOM, level);
                            cow.setPos(this.targetAnimal.getX(), this.targetAnimal.getY(), this.targetAnimal.getZ());
                            this.targetAnimal.remove(Entity.RemovalReason.DISCARDED);
                            level.addFreshEntity(cow);
                            cow.playSound(SoundEvents.PANDA_SNEEZE, 1, 0.3f);
                            FeywildMod.getNetwork().sendParticles(this.entity.level(), ParticleMessage.Type.SHROOMLING_SNEEZE, cow.getX(), cow.getY(), cow.getZ());
                        }
                    }
                    // ADD MUSHROOM
                    if (level.getBrightness(LightLayer.BLOCK, this.entity.blockPosition()) < 3
                            && this.entity.level().getBlockState(this.entity.blockPosition()).canBeReplaced(Fluids.WATER)
                            && (this.entity.level().getBlockState(this.entity.blockPosition().below()).is(BlockTags.DIRT)
                            || this.entity.level().getBlockState(this.entity.blockPosition().below()).is(Blocks.FARMLAND))) {
                        this.level.setBlock(this.entity.blockPosition(), ModBlocks.feyMushroom.defaultBlockState(), 2);
                        this.entity.playSound(SoundEvents.COMPOSTER_READY, 1, 1);
                    }
                } else {
                    // IF NOT TAMED POISON TARGET PLAYER
                    if (this.targetPlayer == null || !this.targetPlayer.isAlive()) {
                        this.targetPlayer = this.findPlayer();
                        if (this.targetPlayer != null && this.targetPlayer.isAlive()) {
                            this.targetPlayer.addEffect(new MobEffectInstance(MobEffects.POISON, 5 * 60, 0));
                        }
                    }
                }
                this.reset();
            } else if (this.ticksLeft == 20) {
                FeywildMod.getNetwork().sendParticles(this.entity.level(), ParticleMessage.Type.SHROOMLING_SNEEZE, this.entity.getX(), this.entity.getY(), this.entity.getZ());
            } else if (this.ticksLeft == 40) {
                this.sneezing();
                this.entity.playSound(ModSoundEvents.shroomlingSneeze.getSoundEvent(), 1, 1);
            }
        }
    }

    private void reset() {
        this.entity.setState(Shroomling.State.IDLE);
        this.targetAnimal = null;
        this.targetPlayer = null;
        this.ticksLeft = -1;
    }

    private void sneezing() {
        this.entity.setState(Shroomling.State.SNEEZING);
    }

    @Override
    public void start() {
        this.ticksLeft = 50;
        this.targetAnimal = null;
        this.targetPlayer = null;
    }

    @Override
    public boolean canUse() {
        return level.random.nextFloat() < 0.01f && !(this.entity.getState() == Shroomling.State.WAVING);
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0 && !(this.entity.getState() == Shroomling.State.WAVING);
    }

    @Nullable
    private Animal findCow() {
        double distance = Double.MAX_VALUE;
        Animal current = null;
        for (Animal animal : this.entity.level().getNearbyEntities(Cow.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8))) {
            if (animal.getAge() == 0 && this.entity.distanceToSqr(animal) < distance) {
                current = animal;
                distance = this.entity.distanceToSqr(animal);
            }
        }
        return current;
    }

    private Player findPlayer() {
        double distance = Double.MAX_VALUE;
        Player current = null;
        for (Player player : this.entity.level().getNearbyEntities(Player.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8))) {
            if (this.entity.distanceToSqr(player) < distance) {
                current = player;
                distance = this.entity.distanceToSqr(player);
            }
        }
        return current;
    }
}
