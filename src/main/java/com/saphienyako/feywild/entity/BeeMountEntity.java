package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.FlyingFeyBase;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class BeeMountEntity extends FlyingFeyBase {

    protected BeeMountEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    //TODO Determines Stats
    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.35)
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    public Alignment getAlignment() {
        return null;
    }

    @Override
    public ItemLike getDismissItem() {
        return null;
    }

    //TODO SOUND IF HAS_BEE_KNIGHT RETURN BEE KNIGHT SOUND ELSE RETURN BEE SOUND BUZZ BUZZ


    @Override
    public SoundEvent getCookieSound() {
        return null;
    }

    @Override
    public SoundEvent getNameSound() {
        return null;
    }

    @Override
    public SoundEvent getSummonSound() {
        return null;
    }

    @Override
    public SoundEvent getDismissSound() {
        return null;
    }

    @Override
    public SoundEvent getFollowSound() {
        return null;
    }

    @Override
    public SoundEvent getStaySound() {
        return null;
    }

    @Override
    public SoundEvent getAbilityOnSound() {
        return null;
    }

    @Override
    public SoundEvent getAbilityOffSound() {
        return null;
    }
}
