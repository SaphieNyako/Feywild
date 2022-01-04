package com.feywild.feywild.entity;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.entity.base.FlyingFeyBase;
import com.feywild.feywild.entity.goals.BeeRestrictAttackGoal;
import com.feywild.feywild.entity.goals.FeyAttackableTargetGoal;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
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
import net.minecraft.world.entity.player.Player;
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
                .add(Attributes.FLYING_SPEED, 2.25);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(2, new FeyAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(1, new BeeRestrictAttackGoal(this, 1.2f, true));
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

    public static void anger(Level level, Player player, BlockPos pos) {
        if (!level.isClientSide && player instanceof ServerPlayer) {
            QuestData quests = QuestData.get((ServerPlayer) player);
            if (quests.getAlignment() != Alignment.SUMMER || quests.getReputation() < MobConfig.bee_knight.required_reputation) {
                AABB aabb = new AABB(pos).inflate(2 * MobConfig.bee_knight.aggrevation_range);
                level.getEntities(ModEntityTypes.beeKnight, aabb, entity -> true).forEach(bee -> {
                    if (bee.getTarget() == null && player.position().closerThan(bee.position(), MobConfig.bee_knight.aggrevation_range)
                            && !player.getGameProfile().getId().equals(bee.getOwner())) {
                        bee.setTarget(player);
                        bee.setAngry(true);
                    }
                });
            }
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
        return ParticleTypes.CRIT;
    }

    @Nonnull
    @Override
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        if (superResult == InteractionResult.PASS) {
            if (!player.level.isClientSide && player instanceof ServerPlayer serverPlayer) {
                QuestData quests = QuestData.get(serverPlayer);
                if (((quests.getAlignment() == Alignment.SUMMER && quests.getReputation() >= MobConfig.bee_knight.required_reputation) && getOwner() == null) || player.getUUID() == owner) {
                    player.sendMessage(new TranslatableComponent("message.feywild.bee_knight_pass"), player.getUUID());
                } else {
                    player.sendMessage(new TranslatableComponent("message.feywild.bee_knight_fail"), player.getUUID());
                }
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
        return SoundEvents.BEE_LOOP;
    }
}
