package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.FlyingFeyBase;
import com.saphienyako.feywild.entity.goals.IronPanicGoal;
import com.saphienyako.feywild.entity.goals.PanicGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class BeeKnightEntity extends FlyingFeyBase {
    public static final EntityDataAccessor<Boolean> AGGRAVATED = SynchedEntityData.defineId(BeeKnightEntity.class, EntityDataSerializers.BOOLEAN);

    protected BeeKnightEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.35)
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.LUCK, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }
/*
    public static void anger(Level level, Player player, BlockPos pos) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {


                AABB aabb = new AABB(pos).inflate(2 * 20);
                level.getEntities(ModEntities.beeKnight, aabb, entity -> true).forEach(bee -> {
                    if (bee.getTarget() == null && player.position().closerThan(bee.position(), 20)
                            && !player.getGameProfile().getId().equals(bee.getOwner())) {
                        bee.setTarget(player);
                        bee.setAngry(true);
                    }
                });

        }
    } */


    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        //target
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, BeeKnightEntity.class)).setAlertOthers());
      //  this.targetSelector.addGoal(2, new BeeKnightAttackableTargetGoal<>(this, Raider.class, true));
     //   this.targetSelector.addGoal(2, new BeeKnightAttackableTargetGoal<>(this, Pillager.class, true));
      //  this.targetSelector.addGoal(2, new FeyAttackableTargetGoal<>(this, Player.class, true));
        //move
    //    this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.0f, 16));
        //attack
     //   this.goalSelector.addGoal(1, new BeeRestrictAttackGoal(this, 1.2f, true));

        //other
        this.goalSelector.addGoal(1, new PanicGoal(this));
        this.goalSelector.addGoal(10, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKIE), false));
        this.goalSelector.addGoal(2, new IronPanicGoal(this, this.level(),0.25, 6 ));
    }

    @Override
    public boolean doHurtTarget(@Nonnull Entity entity) {
        if (entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 5, 1));
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public Alignment getAlignment() {
        return Alignment.SUMMER;
    }

    @Override
    public ItemLike getDismissItem() {
        return null;
    }

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
