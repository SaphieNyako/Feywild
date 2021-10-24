package com.feywild.feywild.entity.mandragora;

import com.feywild.feywild.entity.base.Mandragora;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import com.feywild.feywild.entity.base.MandragoraEntity.MandragoraVariant;

public class PotatoMandragora extends Mandragora {

    public PotatoMandragora(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.entityData.set(VARIANT, 2);
    }

    @Override
    public MandragoraVariant getVariation() {
        return MandragoraVariant.POTATO;
    }
}
