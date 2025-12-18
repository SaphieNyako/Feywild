package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.entity.goals.BreedAbilityGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public class SpringPixieEntity extends PixieBase {

    protected SpringPixieEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new BreedAbilityGoal(this, this.level()));
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.SPRING_SPARKLE_PARTICLE.get();
    }

    @Override
   public Alignment getAlignment() {
        return Alignment.SPRING;
    }

    @Override
    public ItemLike getDismissItem() {
        return ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get();
    }
}
