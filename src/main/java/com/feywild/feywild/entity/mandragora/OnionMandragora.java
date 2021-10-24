package com.feywild.feywild.entity.mandragora;

import com.feywild.feywild.entity.base.Mandragora;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import com.feywild.feywild.entity.base.MandragoraEntity.MandragoraVariant;

public class OnionMandragora extends Mandragora {

    public OnionMandragora(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.entityData.set(VARIANT, 1);
    }

    @Override
    public MandragoraVariant getVariation() {
        return MandragoraVariant.ONION;
    }
}
