package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.MandragoraEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class OnionMandragoraEntity extends MandragoraEntity {

    protected OnionMandragoraEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.entityData.set(VARIANT, 1);
    }

    @Override
    public MandragoraVariant getVariation() {
        return MandragoraVariant.ONION;
    }
}
