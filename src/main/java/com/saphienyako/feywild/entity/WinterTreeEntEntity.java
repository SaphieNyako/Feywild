package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class WinterTreeEntEntity extends TreeEntBase {
    public WinterTreeEntEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public MobEffect getEffect() {
        return ModEffects.WINTER_TREE_ENT_PROTECTION.get();
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.WINTER_LEAF_PARTICLE.get();
    }


    @Override
    public Alignment getAlignment() {
        return Alignment.WINTER;
    }

    @Override
    public ItemLike getDismissItem() {
        return ModItems.SUMMONING_SCROLL_WINTER_TREE_ENT.get();
    }
}
