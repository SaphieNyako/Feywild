package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class TargetBreedGoal extends Goal {

    private static final EntityPredicate TARGETING = (new EntityPredicate()).range(8.0D).allowInvulnerable().allowSameTeam().allowUnseeable();
    private static final int ANIM_OPEN = 0;
    protected final World worldLevel;
    protected SpringPixieEntity entity;
    protected AnimalEntity targetAnimal;
    protected AnimalEntity partner;
    protected boolean enchantAnimalsNearby = false;
    protected int count = 0;
    private Vector3d targetPos;

    public TargetBreedGoal(SpringPixieEntity entity) {
        this.entity = entity;
        this.worldLevel = entity.level;

    }

    private AnimalEntity selectTargetAnimal(SpringPixieEntity entity) {

        List<AnimalEntity> animalsNearEntity = entity.level.getNearbyEntities(AnimalEntity.class, TARGETING, entity, entity.getBoundingBox().inflate(8.0D));
        double d0 = Double.MAX_VALUE;
        AnimalEntity animal = null;

        for (AnimalEntity targetAnimal : animalsNearEntity) {
            if (entity.distanceToSqr(targetAnimal) < d0) {
                animal = targetAnimal;
                d0 = entity.distanceToSqr(targetAnimal);
            }
        }
        return animal;
    }

    @Override
    public void tick() {

        if (selectTargetAnimal(entity) != null) {
            targetAnimal = selectTargetAnimal(entity);
            if (targetAnimal.canFallInLove()) {
                partner = getFreePartner(targetAnimal);
                if (partner != null) {
                    enchantAnimalsNearby = true;
                }

            }
        }

        if (enchantAnimalsNearby) {
            count--;

            if (count <= 0 && partner != null) {
                breed(targetAnimal, partner);
                reset();

            } else if (count == 110) {
                spellCasting();

            } else if (count <= 100) {
                entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
                entity.getNavigation().moveTo(targetAnimal.getX(), targetAnimal.getY(), targetAnimal.getZ(), 0.5);
            }
        }
    }

    @Override
    public void start() {
        count = 120;
    }

    private void spellCasting() {

        this.targetPos = new Vector3d(targetAnimal.getX(), targetAnimal.getY(), targetAnimal.getZ());

        entity.setCasting(true);
        entity.playSound(ModSoundEvents.PIXIE_SPELLCASTING.get(), 1, 1);
    }

    private AnimalEntity getFreePartner(AnimalEntity animalEntity) {

        List<AnimalEntity> animalsNearAnimalEntity = animalEntity.level.getNearbyEntities(animalEntity.getClass(), TARGETING, animalEntity, animalEntity.getBoundingBox().inflate(8.0D));
        double d0 = Double.MAX_VALUE;
        AnimalEntity animalentity = null;

        for (AnimalEntity animalentity1 : animalsNearAnimalEntity) {

            if (animalEntity.distanceToSqr(animalentity1) < d0) {
                animalentity = animalentity1;
                d0 = animalEntity.distanceToSqr(animalentity1);
            }
        }
        return animalentity;

    }

    protected void breed(AnimalEntity animalEntity, AnimalEntity partner) {
        animalEntity.spawnChildFromBreeding((ServerWorld) animalEntity.level, partner);

        //For a better effect we should add our own particles
        FeywildPacketHandler.sendToPlayersInRange(worldLevel, entity.blockPosition()
                , new ParticleMessage(this.entity.blockPosition().getX(), this.entity.blockPosition().getY() + 1, this.entity.blockPosition().getZ(), this.targetAnimal.blockPosition().getX() + 0.5, this.targetAnimal.blockPosition().getY() + 0.5, this.targetAnimal.blockPosition().getZ() + 0.5, -10, 6, 0.11f)
                , 64);

    }

    protected void reset() {
        enchantAnimalsNearby = false;
        entity.setCasting(false);
        partner = null;
        targetAnimal = null;
        count = 0;
    }

    @Override
    public boolean canContinueToUse() {

        return enchantAnimalsNearby;
    }

    @Override
    public boolean canUse() {
        return entity.level.random.nextFloat() < 0.005f;
    }

}
