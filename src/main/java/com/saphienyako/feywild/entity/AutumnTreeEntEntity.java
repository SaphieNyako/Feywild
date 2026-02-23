package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.TreeEntBase;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AutumnTreeEntEntity extends TreeEntBase {
    public AutumnTreeEntEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.AUTUMN_LEAF_PARTICLE.get();
    }


    @Override
    public Alignment getAlignment() {
        return Alignment.AUTUMN;
    }

    @Override
    public ItemLike getDismissItem() {
        return ModItems.SUMMONING_SCROLL_AUTUMN_TREE_ENT.get();
    }
}
