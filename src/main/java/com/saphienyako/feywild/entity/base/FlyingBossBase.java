package com.saphienyako.feywild.entity.base;

import com.saphienyako.feywild.entity.base.intereface.FlyingEntity;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public abstract class FlyingBossBase extends BossBase implements FlyingEntity {



    protected FlyingBossBase(EntityType<? extends PathfinderMob> entity, Level level, ServerBossEvent bossInfo) {
        super(entity, level, bossInfo);
        this.moveControl = new FlyingMoveControl(this, 4, true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.registerFlyingGoals(this);
    }

    @Override
    public void travel(@Nonnull Vec3 to) {
        this.flyingTravel(this, to);
    }

    @Nonnull
    @Override
    protected PathNavigation createNavigation(@Nonnull Level level) {
        return this.createFlyingNavigation(this, level);
    }
}
