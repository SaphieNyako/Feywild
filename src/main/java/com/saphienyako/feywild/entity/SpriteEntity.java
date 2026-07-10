package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.FlyingFeyBase;
import com.saphienyako.feywild.entity.goals.SpriteAngryGoal;
import com.saphienyako.feywild.entity.goals.SpriteHappyGoal;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;
import java.util.function.Consumer;

public class SpriteEntity extends FlyingFeyBase {

    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(SpriteEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SpriteEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MODE = SynchedEntityData.defineId(SpriteEntity.class, EntityDataSerializers.INT);
    public final AnimationState FLY_IDLE_ANIMATION = new AnimationState();
    public final AnimationState FLY_ANIMATION = new AnimationState();

    public final AnimationState HAPPY_ANIMATION = new AnimationState();

    public final AnimationState ANGRY_ANIMATION = new AnimationState();

    public static final double MIN_MOVING_SPEED_SQR = 1.0E-6;
    private int movingTicks = 0;

    public SpriteEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
        //  this.entityData.set(VARIANT, SpriteVariant.HEXEN.ordinal());
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

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new SpriteAngryGoal(this));
        this.goalSelector.addGoal(2, new SpriteHappyGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
        this.entityData.define(VARIANT, SpriteVariant.HEXEN.ordinal());
        this.entityData.define(MODE, Mode.DEFAULT.ordinal());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("SpriteVariant", this.entityData.get(VARIANT));
        nbt.putInt("SpriteMode", this.entityData.get(MODE));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("SpriteVariant")) {
            this.entityData.set(VARIANT, nbt.getInt("SpriteVariant"));
        }
        if (nbt.contains("SpriteMode")) {
            this.entityData.set(MODE, nbt.getInt("SpriteMode"));
        }
    }

    @Override
    public void tick() {
        super.tick();
        //Visuals
        if (this.level().isClientSide()) {
            setupAnimationStates();

            if (this.getParticle() != null && random.nextInt(11) == 0) {
                for (int i = 0; i < 4; i++) {
                    this.level().addParticle(
                            this.getParticle(),
                            this.getX() + (Math.random() - 0.5),
                            this.getY() + 1 + (Math.random() - 0.5),
                            this.getZ() + (Math.random() - 0.5),
                            0, 0, 0
                    );
                }
            }

            return;
        }

        //server
        if (this.getMode() != Mode.PROJECTILE) {
            return;
        }

        // movement
        if (this.tickCount < 40) {
            LivingEntity target = this.getTarget();

            if (target != null) {
                Vec3 adjust = target.position()
                        .subtract(this.position())
                        .normalize()
                        .scale(0.02);

                this.setDeltaMovement(this.getDeltaMovement().add(adjust));
            }
        }
        this.move(MoverType.SELF, this.getDeltaMovement());

        boolean shouldExpire = this.tickCount > 60;

        if (shouldExpire && isPlayerNearby(3.0)) {
            onImpact();
            this.discard();
        }

        if (this.tickCount > 20 * 120) {
            this.discard();
        }
    }

    private boolean isPlayerNearby(double radius) {
        return !this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(radius)).isEmpty();
    }

    private void onImpact() {
        if (this.level().isClientSide()) return;
        if (this.getMode() != Mode.PROJECTILE) return;

        SpriteVariant variant = this.getVariant();
        ServerLevel level = (ServerLevel) this.level();

        Vec3 pos = this.position();

        switch (variant) {

            case SUMMER -> {
                level.explode(this, pos.x, pos.y, pos.z, 2.0F, Level.ExplosionInteraction.NONE);
                applyAoE(entity -> {
                    entity.setRemainingFireTicks(6 * 20);
                    this.playSound(ModSounds.SUMMER_PIXIE_GIGGLE.get(), 1, 1);
                    if (entity.getRandom().nextFloat() < 0.1f) {
                        entity.playSound(ModSounds.TITANIA_SUMMER.get(), 1.0F, 1.0F);
                    }
                });

            }

            case WINTER -> {
                applyAoE(entity -> {

                    entity.setTicksFrozen(entity.getTicksRequiredToFreeze());
                    entity.setTicksFrozen(entity.getTicksFrozen() + 60);

                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                    entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
                    this.playSound(ModSounds.WINTER_PIXIE_GIGGLE.get(), 1, 1);
                    if (entity.getRandom().nextFloat() < 0.1f) {
                        entity.playSound(ModSounds.TITANIA_WINTER.get(), 1.0F, 1.0F);
                    }
                });

            }

            case SPRING -> {
                applyAoE(entity -> {
                    entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 80, 1));
                    this.playSound(ModSounds.SPRING_PIXIE_GIGGLE.get(), 1, 1);
                    if (entity.getRandom().nextFloat() < 0.1f) {
                        entity.playSound(ModSounds.TITANIA_SPRING.get(), 1.0F, 1.0F);
                    }
                });
            }

            case AUTUMN -> {
                applyAoE(entity -> {
                    entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 1));
                    this.playSound(ModSounds.AUTUMN_PIXIE_GIGGLE.get(), 1, 1);
                    if (entity.getRandom().nextFloat() < 0.1f) {
                        entity.playSound(ModSounds.TITANIA_AUTUMN.get(), 1.0F, 1.0F);
                    }
                });
            }

            case HEXEN -> {
                applyAoE(entity -> {
                    entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 80, 1));
                    this.playSound(ModSounds.AUTUMN_PIXIE_GIGGLE.get(), 1, 1);
                    if (entity.getRandom().nextFloat() < 0.05f) {
                        entity.playSound(ModSounds.TITANIA_HEXEN.get(), 1.0F, 1.0F);
                    }
                });
            }

            case BLOSSOM -> {
                Vec3 center = this.position();
                double radius = 6.0;

                List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(radius));

                for (LivingEntity entity : entities) {
                    if (entity == this) continue;

                    Vec3 pushDir = entity.position()
                            .subtract(center)
                            .normalize();

                    double strength = 1.2;

                    entity.setDeltaMovement(entity.getDeltaMovement().add(pushDir.x * strength, 1.5, pushDir.z * strength));
                    entity.hurtMarked = true;
                    this.playSound(ModSounds.SPRING_PIXIE_GIGGLE.get(), 1, 1);
                    if (entity.getRandom().nextFloat() < 0.1f) {
                        entity.playSound(ModSounds.TITANIA_BLOSSOM.get(), 1.0F, 1.0F);
                    }
                }
            }
        }

        level.sendParticles(
                ParticleTypes.END_ROD,
                pos.x,
                pos.y,
                pos.z,
                10,
                0.3, 0.3, 0.3,
                0.15
        );

        this.discard();
    }

    private void applyAoE(Consumer<LivingEntity> action) {
        double radius = 4.0;

        List<LivingEntity> entities = this.level().getEntitiesOfClass(
                LivingEntity.class,
                this.getBoundingBox().inflate(radius)
        );

        for (LivingEntity entity : entities) {
            if (entity == this) continue;
            if (entity instanceof TitaniaEntity) continue;
            action.accept(entity);
        }
    }



    @Override
    public SimpleParticleType getParticle() {
        return this.getVariant().getParticle();
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > MIN_MOVING_SPEED_SQR;
    }

    private boolean isActuallyMoving() {
        if (isMoving()) {
            movingTicks = 5;
        } else {
            movingTicks = Math.max(0, movingTicks - 1);
        }
        return movingTicks > 0;
    }

    private void setupAnimationStates() {

        if (getState() != SpriteEntity.State.HAPPY && getState() != SpriteEntity.State.ANGRY ) {
            if (isActuallyMoving()) {
                if (!FLY_ANIMATION.isStarted()) {
                    FLY_ANIMATION.start(this.tickCount);
                }
                FLY_IDLE_ANIMATION.stop();
            } else {
                if (!FLY_IDLE_ANIMATION.isStarted()) {
                    FLY_IDLE_ANIMATION.start(this.tickCount);
                }
                FLY_ANIMATION.stop();
            }
        }

        // HAPPY
        if (getState() == SpriteEntity.State.HAPPY && getState() != SpriteEntity.State.ANGRY) {
            if (!HAPPY_ANIMATION.isStarted()) {
                HAPPY_ANIMATION.start(this.tickCount);
            }
        } else {
            HAPPY_ANIMATION.stop();
        }

        // ANGRY
        if (getState() == SpriteEntity.State.ANGRY && getState() != SpriteEntity.State.HAPPY) {
            if (!ANGRY_ANIMATION.isStarted()) {
                ANGRY_ANIMATION.start(this.tickCount);
            }
        } else {
            ANGRY_ANIMATION.stop();
        }
    }

    @Override
    public Alignment getAlignment() {
        return Alignment.HEXEN;
    }

    @Override
    public ItemLike getDismissItem() {
        return ItemStack.EMPTY.getItem();
    }

    //TODO SOUND

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
        return SoundEvents.ALLAY_AMBIENT_WITH_ITEM;
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

    public SpriteEntity.State getState() {
        SpriteEntity.State[] states = SpriteEntity.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }


    public void setState(SpriteEntity.State state) {
        this.entityData.set(STATE, state.ordinal());
    }


    public SpriteEntity.SpriteVariant getVariant() {
        return SpriteEntity.SpriteVariant.values()[this.entityData.get(VARIANT)];
    }

    public void setVariant(SpriteEntity.SpriteVariant variant) {this.entityData.set(VARIANT, variant.ordinal());}


    public Mode getMode() {
        return Mode.values()[this.entityData.get(MODE)];
    }

    public void setMode(Mode mode) {
        this.entityData.set(MODE, mode.ordinal());
    }

    public enum State {
        IDLE, POSE, ANGRY, HAPPY
    }

    public enum SpriteVariant {

        SPRING(ModParticles.SPRING_SPARKLE_PARTICLE),
        SUMMER(ModParticles.SUMMER_SPARKLE_PARTICLE),
        WINTER(ModParticles.WINTER_SPARKLE_PARTICLE),
        AUTUMN(ModParticles.AUTUMN_SPARKLE_PARTICLE),
        HEXEN(ModParticles.HEXEN_SPARKLE_PARTICLE),
        BLOSSOM(ModParticles.HEXEN_SPARKLE_PARTICLE);

        private final RegistryObject<SimpleParticleType> particle;

        SpriteVariant(RegistryObject<SimpleParticleType> particle) {
            this.particle = particle;
        }

        public SimpleParticleType getParticle() {
            return particle.get();
        }
    }

    public enum Mode {
        DEFAULT, PROJECTILE
    }

}
