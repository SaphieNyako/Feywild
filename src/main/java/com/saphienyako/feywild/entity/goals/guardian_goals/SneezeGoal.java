package com.saphienyako.feywild.entity.goals.guardian_goals;

import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.MooShroomCowEntity;
import com.saphienyako.feywild.entity.ShroomlingEntity;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;

public class SneezeGoal extends Goal {

    private static final TargetingConditions TARGETING = TargetingConditions.forNonCombat().range(8).ignoreLineOfSight();

    protected final Level level;
    protected final ShroomlingEntity entity;
    private int ticksLeft = 0;
    private Animal targetAnimal;

    public SneezeGoal(ShroomlingEntity shroomling) {
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
                        if (this.targetAnimal != null && this.targetAnimal.isAlive()&& !(this.targetAnimal instanceof MushroomCow)) {
                                    PacketDistributor.sendToPlayersTrackingEntity(
                                    this.targetAnimal,
                                    new ParticleMessage(
                                            ParticleMessage.Particles.SHROOMLING_SNEEZE,
                                            this.targetAnimal.blockPosition().above()
                                    )
                            );
                            this.targetAnimal.playSound(SoundEvents.PANDA_SNEEZE);
                            this.targetAnimal.remove(Entity.RemovalReason.DISCARDED);
                            level.addFreshEntity(getMushroomCow());
                        }
                    }
                }
                this.reset();
            } else if (this.ticksLeft == 20) {
                //SHROOMLING SNEEZE
                PacketDistributor.sendToPlayersTrackingEntity(
                        entity,
                        new ParticleMessage(
                                ParticleMessage.Particles.SHROOMLING_SNEEZE,
                                entity.blockPosition().above()
                        )
                );
            } else if (this.ticksLeft == 35) {
                this.sneezing();
                //TODO add Sound
                this.entity.playSound(entity.getSneezeSound(), 1, 1);
            }
        }
    }

    private Entity getMushroomCow() {
        double x = this.targetAnimal.getX();
        double y = this.targetAnimal.getY();
        double z = this.targetAnimal.getZ();

        if (entity.getVariant() == ShroomlingEntity.ShroomlingVariant.DEFAULT ||
                entity.getVariant() == ShroomlingEntity.ShroomlingVariant.BROWN) {

            MushroomCow cow = new MushroomCow(EntityType.MOOSHROOM, level);
            cow.setPos(x, y, z);

            if (entity.getVariant() == ShroomlingEntity.ShroomlingVariant.BROWN) {
                cow.setVariant(MushroomCow.MushroomType.BROWN);
            } else {
                cow.setVariant(MushroomCow.MushroomType.RED);
            }

            return cow;
        }

        MooShroomCowEntity cowEntity = new MooShroomCowEntity(ModEntities.MOO_SHROOM_COW.get(), level);
        cowEntity.setPos(x, y, z);

        cowEntity.setMooShroomVariant(matchVariant(entity.getVariant()));

        return cowEntity;
    }

    public static MooShroomCowEntity.MooShroomCowVariant matchVariant(ShroomlingEntity.ShroomlingVariant variant) {
        return switch (variant) {
            case ORANGE -> MooShroomCowEntity.MooShroomCowVariant.ORANGE;
            case YELLOW -> MooShroomCowEntity.MooShroomCowVariant.YELLOW;
            case GREEN -> MooShroomCowEntity.MooShroomCowVariant.GREEN;
            case LIGHT_BLUE -> MooShroomCowEntity.MooShroomCowVariant.LIGHT_BLUE;
            case BLUE -> MooShroomCowEntity.MooShroomCowVariant.BLUE;
            case PURPLE -> MooShroomCowEntity.MooShroomCowVariant.PURPLE;
            case PINK -> MooShroomCowEntity.MooShroomCowVariant.PINK;
            default -> null;
        };
    }

    private void reset() {
        this.entity.setState(ShroomlingEntity.State.IDLE);
        this.targetAnimal = null;
        this.ticksLeft = -1;
    }

    private void sneezing() {
        this.entity.setState(ShroomlingEntity.State.SNEEZE);
    }

    @Override
    public void start() {
        this.ticksLeft = 45;
        this.targetAnimal = null;
    }

    @Override
    public boolean canUse() {
        Player owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayer && this.entity.getAbilityActive()) {
            return this.level.random.nextFloat() < 0.005f;
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0 && !(this.entity.getState() == ShroomlingEntity.State.WAVE);
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
}
