package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.SpringPixieEntity;
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
        AnimalEntity animalentity = null;

        for (AnimalEntity targetAnimal : animalsNearEntity) {
            if (entity.distanceToSqr(targetAnimal) < d0) {
                animalentity = targetAnimal;
                d0 = entity.distanceToSqr(targetAnimal);
            }
        }
        return animalentity; //If this return null game crashes
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
            //First move to location
            count++;
            if (count == 1) {
                entity.getNavigation().moveTo(targetAnimal.getX(), targetAnimal.getY(), targetAnimal.getZ(), 0.5);

                this.targetPos = new Vector3d(targetAnimal.getX(), targetAnimal.getY(), targetAnimal.getZ());

                entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
                entity.setCasting(true);
                entity.playSound(ModSoundEvents.PIXIE_SPELLCASTING.get(), 1, 1);

            }
            if (count == 120 && partner != null) {
                breed(targetAnimal, partner);

                enchantAnimalsNearby = false;
                entity.setCasting(false);
                partner = null;
                targetAnimal = null;
                count = 0;

            }
        }
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
