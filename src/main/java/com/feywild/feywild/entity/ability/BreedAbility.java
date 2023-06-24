package com.feywild.feywild.entity.ability;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.Pixie;
import com.feywild.feywild.network.ParticleMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class BreedAbility implements Ability<BreedAbility.BreedData> {

    private static final TargetingConditions TARGETING = TargetingConditions.forNonCombat().range(8).ignoreLineOfSight();

    @Nullable
    @Override
    public BreedData init(Level level, Pixie pixie) {
        Animal target = findTarget(level, pixie);
        if (target == null) return null;
        Animal partner = findPartner(level, target);
        if (partner == null) return null;
        return new BreedData(target, partner);
    }

    @Override
    public boolean stillValid(Level level, Pixie pixie, BreedData data) {
        return data.target().isAlive() && data.partner().isAlive();
    }

    @Override
    public void perform(Level level, Pixie pixie, BreedData data) {
        if (level instanceof ServerLevel serverLevel) {
            data.target().spawnChildFromBreeding(serverLevel, data.partner());
            FeywildMod.getNetwork().sendParticles(level, ParticleMessage.Type.ANIMAL_BREED, pixie.getX(), pixie.getY(), pixie.getZ(), data.target().getX(), data.target().getY(), data.target().getZ());
        }
    }

    @Nullable
    @Override
    public Vec3 target(Level level, Pixie pixie, BreedData data) {
        return data.target().position();
    }

    @Nullable
    private Animal findTarget(Level level, Pixie pixie) {
        double distanceSqr = Double.MAX_VALUE;
        Animal current = null;
        for (Animal animal : level.getNearbyEntities(Animal.class, TARGETING, pixie, pixie.getBoundingBox().inflate(8))) {
            if (animal.getAge() >= 0 && pixie.distanceToSqr(animal) < distanceSqr) {
                current = animal;
                distanceSqr = pixie.distanceToSqr(animal);
            }
        }
        return current;
    }

    @Nullable
    private Animal findPartner(Level level, Animal target) {
        double distanceSqr = Double.MAX_VALUE;
        Animal current = null;
        for (Animal animal : level.getNearbyEntities(Animal.class, TARGETING, target, target.getBoundingBox().inflate(8))) {
            if (animal != target && animal.getAge() >= 0 && target.distanceToSqr(animal) < distanceSqr) {
                // We need to set both entities in love to get correct results
                // from canMate. So we store the old love time to set it back later
                int oldInLove1 = target.getInLoveTime();
                int oldInLove2 = animal.getInLoveTime();
                target.setInLoveTime(1);
                animal.setInLoveTime(1);
                if (target.canMate(animal) && target.distanceToSqr(animal) < distanceSqr) {
                    current = animal;
                    distanceSqr = target.distanceToSqr(animal);
                }
                target.setInLoveTime(oldInLove1);
                animal.setInLoveTime(oldInLove2);
            }
        }
        return current;
    }

    protected record BreedData(Animal target, Animal partner) {}
}
