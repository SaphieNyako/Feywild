package com.saphienyako.feywild.entity.base;

import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.SpriteEntity;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BossBase extends PathfinderMob {

    private static final int RETALIATION_WINDOW = 20 * 3;

    @Nullable
    private Player recentAttacker;

    private int recentAttackerTicks;

    public final ServerBossEvent bossInfo;
    private int deathTicks = 0;
    private boolean dying = false;
    protected BossBase(EntityType<? extends PathfinderMob> entity, Level level, ServerBossEvent bossInfo) {
        super(entity, level);
        this.bossInfo = bossInfo;
        this.xpReward = 50;
        this.setHealth(getMaxHealth());
    }

    public static boolean canSpawn(LevelAccessor level, BlockPos pos) {
        return isBrightEnoughToSpawn(level, pos);
    }

    protected static boolean isBrightEnoughToSpawn(LevelReader level, BlockPos pos) {
        return level.getRawBrightness(pos, 0) > 8;
    }

    @Override
    public void setCustomName(Component name) {
        super.setCustomName(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    protected void customServerAiStep(@NotNull ServerLevel level) {
        super.customServerAiStep(level);
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void startSeenByPlayer(@Nonnull ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.bossInfo.addPlayer(serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(@Nonnull ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossInfo.removePlayer(serverPlayer);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    /** @noinspection resource*/
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            tickRecentAttacker();
        }

        if (dying) {
            tickDeathSequence();
        }
    }

    private void tickRecentAttacker() {
        if (recentAttackerTicks > 0) {
            recentAttackerTicks--;
        }

        if (recentAttackerTicks <= 0
                || recentAttacker == null
                || !recentAttacker.isAlive()
                || recentAttacker.isRemoved()) {

            recentAttacker = null;
            recentAttackerTicks = 0;
        }
    }

    public boolean wasRecentlyHurtByPlayer() {
        return recentAttacker != null && recentAttackerTicks > 0 && recentAttacker.isAlive();
    }

    @Nullable
    public Player getRecentAttacker() {
        return wasRecentlyHurtByPlayer() ? recentAttacker : null;
    }

    @Override
    protected void tickDeath() {
        //override
    }

    @Override
    protected void actuallyHurt(@NotNull ServerLevel level, @NotNull DamageSource source, float amount) {
        if (dying) {
            return;
        }

        super.actuallyHurt(level, source, amount);

        if (this.getHealth() <= 0.0F) {
            this.setHealth(1.0F);
            beginDeathSequence();
            return;
        }

        Entity attacker = source.getEntity();

        if (attacker instanceof Player player) {
            recentAttacker = player;
            recentAttackerTicks = RETALIATION_WINDOW;
            this.setTarget(player);
        }
    }

    @SuppressWarnings("resource")
    private void beginDeathSequence() {

        this.dying = true;
        this.deathTicks = 0;
        this.setNoAi(true);
        this.setInvulnerable(true);
        this.setDeltaMovement(Vec3.ZERO);
        if (this.level().isClientSide()) {
            stopSoundOnDeath();
        }
    }
    @SuppressWarnings("resource")
    private void tickDeathSequence() {
        deathTicks++;

        //Slowly floating upward
        this.setDeltaMovement(0, 0.02, 0);

        //Particles
        if (level() instanceof ServerLevel server) {

            server.sendParticles(
                    getParticle(),
                    getX(),
                    getY() + 1,
                    getZ(),
                    6,
                    0.5,
                    0.5,
                    0.5,
                    0.02
            );

            if (deathTicks >= 150) {
                PacketDistributor.sendToPlayersTrackingEntity(
                        this,
                        new ParticleMessage(
                                ParticleMessage.Particles.DANDELION_FLUFF,
                                this.blockPosition().above()
                        )
                );
            }
        }

        //Explosion bursts
        if (deathTicks > 80 && deathTicks % 10 == 0) {

            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.EXPLOSION,
                        getX(),
                        getY() + 1.0D,
                        getZ(),
                        1,
                        0.4D,
                        0.6D,
                        0.4D,
                        0.0D
                );
            }

            level().playSound(
                    null,
                    getX(),
                    getY(),
                    getZ(),
                    SoundEvents.GENERIC_EXPLODE,
                    SoundSource.HOSTILE,
                    0.30F,
                    1.0F
            );
        }

        //Loot burst
        if (deathTicks == 155) {
            scatterBossLoot();
        }

        //Removal
        if (deathTicks >= 160) {
            addSprite();
            this.remove(RemovalReason.KILLED);
            this.gameEvent(GameEvent.ENTITY_DIE);

        }
    }
    @SuppressWarnings("resource")
    private void addSprite(){
        SpriteEntity entity = ModEntities.SPRITE.get().create(level(), EntitySpawnReason.TRIGGERED);

        if (entity == null) return;

        entity.setVariant(getSpriteVariant());
        entity.setPos(this.getX(), this.getY() + 1, this.getZ());
        level().addFreshEntity(entity);
    }

    private void scatterBossLoot() {
        int dustAmount = random.nextInt(24, 42);
        scatterDust(dustAmount);
    }
    @SuppressWarnings("resource")
    public void scatterItem(ItemStack stack) {

        ItemEntity item = new ItemEntity(
                level(),
                getX(),
                getY() + 1,
                getZ(),
                stack
        );

        double angle = random.nextDouble() * Math.PI * 2.0;
        double speed = 0.2 + random.nextDouble() * 0.3;

        double motionX = Math.cos(angle) * speed;
        double motionZ = Math.sin(angle) * speed;

        item.setDeltaMovement(
                motionX,
                0.3 + random.nextDouble() * 0.2,
                motionZ
        );

        level().addFreshEntity(item);
    }

    public void scatterDust(int totalAmount) {

        int remaining = totalAmount;

        while (remaining > 0) {

            int stackSize = Math.min(
                    remaining,
                    random.nextInt(1, 2)
            );

            remaining -= stackSize;

            ItemStack stack = new ItemStack(
                    ModItems.FEY_DUST.get(),
                    stackSize
            );

            scatterItem(stack);
        }
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    @Override
    protected int calculateFallDamage(double fallDistance, float damageModifier) {
        return 0;
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageModifier, @NotNull DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected boolean canRide(@Nonnull Entity entityIn) {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceSq) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canTeleport(Level from, Level to) {
        return from.dimension().equals(to.dimension());
    }

    @Override
    public boolean ignoreExplosion(@Nonnull Explosion explosion) {
        return true;
    }

    @Override
    public float getVoicePitch() {
        return 1.0f;
    }

    public abstract SoundEvent getSummonSound();

    public abstract SimpleParticleType getParticle();

    public abstract SpriteEntity.SpriteVariant getSpriteVariant();

    @Override
    protected void playHurtSound(@NotNull DamageSource source) {
        SoundEvent sound = this.getHurtSound(source);

        if (sound != null) {
            this.playSound(sound, 0.6F, this.getVoicePitch());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void stopAmbientSound() {
        SoundEvent ambientSound = this.getAmbientSound();

        if (ambientSound != null) {
            Minecraft.getInstance()
                    .getSoundManager()
                    .stop(ambientSound.location(), this.getSoundSource());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void stopSoundOnDeath() {
        SoundEvent ambientSound = this.getAmbientSound();

        if (ambientSound != null) {
            Minecraft.getInstance()
                    .getSoundManager()
                    .stop(ambientSound.location(), this.getSoundSource());
        }
    }
}
