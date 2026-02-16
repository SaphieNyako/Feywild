package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SummerTreeEntEntity extends TreeEntBase {
    public SummerTreeEntEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return null;
    }


    @Override
    public Alignment getAlignment() {
        return Alignment.SUMMER;
    }

    @Override
    public ItemLike getDismissItem() {
        //TODO add mapping
        return null;
    }
}
