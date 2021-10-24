package com.feywild.feywild.entity.mandragora;

import com.feywild.feywild.entity.base.Mandragora;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class TomatoMandragora extends Mandragora {

    public TomatoMandragora(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.entityData.set(VARIANT, 4);
    }

    @Override
    public MandragoraVariant getVariation() {
        return MandragoraVariant.TOMATO;
    }
}
