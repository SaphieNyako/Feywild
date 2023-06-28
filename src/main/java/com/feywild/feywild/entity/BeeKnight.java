package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.entity.base.FlyingFeyBase;
import com.feywild.feywild.entity.goals.FeyAttackableTargetGoal;
import com.feywild.feywild.entity.goals.GoToTargetPositionGoal;
import com.feywild.feywild.entity.goals.beeknight.BeeKnightAttackableTargetGoal;
import com.feywild.feywild.entity.goals.beeknight.BeeRestrictAttackGoal;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.tag.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class BeeKnight extends FlyingFeyBase {

    public static final EntityDataAccessor<Boolean> AGGRAVATED = SynchedEntityData.defineId(BeeKnight.class, EntityDataSerializers.BOOLEAN);

    public BeeKnight(EntityType<? extends BeeKnight> type, Level level) {
        super(type, Alignment.SUMMER, level);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return FeyBase.getDefaultAttributes()
                .add(Attributes.MAX_HEALTH, 24)
                .add(Attributes.FOLLOW_RANGE, 80)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.FLYING_SPEED, 2.25);
    }

    public static void anger(Level level, Player player, BlockPos pos) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            QuestData quests = QuestData.get(serverPlayer);
            if (quests.getAlignment() != Alignment.SUMMER || quests.getReputation() < MobConfig.bee_knight.required_reputation) {
                AABB aabb = new AABB(pos).inflate(2 * MobConfig.bee_knight.aggrevation_range);
                level.getEntities(ModEntities.beeKnight, aabb, entity -> true).forEach(bee -> {
                    if (bee.getTarget() == null && player.position().closerThan(bee.position(), MobConfig.bee_knight.aggrevation_range)
                            && !player.getGameProfile().getId().equals(bee.getOwner())) {
                        bee.setTarget(player);
                        bee.setAngry(true);
                    }
                });
            }
        }
    }


    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        //super.registerGoals();
        //target
        // this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, BeeKnight.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new BeeKnightAttackableTargetGoal(this, Raider.class, true));
        this.targetSelector.addGoal(2, new BeeKnightAttackableTargetGoal(this, Pillager.class, true));
        this.targetSelector.addGoal(2, new FeyAttackableTargetGoal<>(this, Player.class, true));
        //move
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 1.0f, 16));
        //attack
        this.goalSelector.addGoal(1, new BeeRestrictAttackGoal(this, 1.2f, true));

        //other
        this.goalSelector.addGoal(50, new WaterAvoidingRandomFlyingGoal(this, 1));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(30, new LookAtPlayerGoal(this, Player.class, 8f));
        this.goalSelector.addGoal(11, new GoToTargetPositionGoal(this, this::getCurrentPointOfInterest, 8, this.getTargetPositionSpeed()));
        this.goalSelector.addGoal(30, new RandomLookAroundGoal(this));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AGGRAVATED, false);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor level, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag nbt) {
        setSummonPos(this.blockPosition());
        return super.finalizeSpawn(level, difficulty, reason, data, nbt);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide && hurtTime > 0 && getLastHurtByMob() != getOwningPlayer()) {
            setTarget(getLastHurtByMob());
            setAngry(true);
        }
    }

    public boolean isAngry() {
        return this.entityData.get(AGGRAVATED);
    }

    public void setAngry(boolean value) {
        this.entityData.set(AGGRAVATED, value);
    }

    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.summerSparkleParticle;
    }

    @Nonnull
    @Override
    @OverridingMethodsMustInvokeSuper
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        if (superResult == InteractionResult.PASS) {
            if (player.getItemInHand(hand).is(ModItemTags.COOKIES) && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
                this.heal(4);
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                FeywildMod.getNetwork().sendParticles(this.level, ParticleMessage.Type.FEY_HEART, this.getX(), this.getY() + 1, this.getZ());
                player.swing(hand, true);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return superResult;
        }
    }

    private <E extends IAnimatable> PlayState flyingPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bee_knight.fly", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<BeeKnight> flyingController = new AnimationController<>(this, "flyingController", 0, this::flyingPredicate);
        animationData.addAnimationController(flyingController);
    }

    @Override
    public boolean doHurtTarget(@Nonnull Entity entity) {
        if (entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 5, 1));
        }
        return super.doHurtTarget(entity);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damage) {
        return this.random.nextBoolean() ? ModSoundEvents.pixieHurt : SoundEvents.BEE_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return this.random.nextBoolean() ? ModSoundEvents.pixieDeath : SoundEvents.BEE_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return switch (random.nextInt(4)) {
            case 0 -> ModSoundEvents.beeKnight;
            default -> SoundEvents.BEE_LOOP;
        };
    }

    @Override
    public boolean canFollowPlayer() {
        return true;
    }
}
