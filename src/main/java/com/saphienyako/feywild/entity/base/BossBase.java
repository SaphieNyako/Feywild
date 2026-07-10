package com.saphienyako.feywild.entity.base;

import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.SpriteEntity;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;


import javax.annotation.Nonnull;

public abstract class BossBase extends PathfinderMob {

    public final ServerBossEvent bossInfo;
    private int deathTicks = 0;
    private boolean dying = false;
    protected BossBase(EntityType<? extends PathfinderMob> entity, Level level, ServerBossEvent bossInfo) {
        super(entity, level);
        this.bossInfo = bossInfo;
        this.noCulling = true;
        this.xpReward = 50;
        this.setHealth(getMaxHealth());
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter getter, BlockPos pos) {
        return getter.getRawBrightness(pos, 0) > 8;
    }

    public ServerBossEvent getBossInfo() {
        return bossInfo;
    }

    @Override
    public void setCustomName(Component name) {
        super.setCustomName(name);
        this.bossInfo.setName(this.getDisplayName());
    }


    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
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
    public void readAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    public void tick() {
        super.tick();
        if (dying) {
            tickDeathSequence();
        }
    }

    @Override
    protected void tickDeath() {
        //override
    }

    @Override
    public boolean hurt(@Nonnull DamageSource source, float amount) {

        if (dying) {
            return false;
        }

        boolean result = super.hurt(source, amount);

        if (this.getHealth() <= 0.0F && !dying) {

            this.setHealth(1.0F);
            beginDeathSequence();
            return false;
        }

        return result;
    }

    private void beginDeathSequence() {

        this.dying = true;
        this.deathTicks = 0;
        this.setNoAi(true);
        this.setInvulnerable(true);
        this.setDeltaMovement(Vec3.ZERO);
    }

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
                FeywildNetwork.sendParticles(this.level(), ParticleMessage.Type.DANDELION_FLUFF, this.blockPosition().above());
            }
        }

        //Explosion bursts
        if (deathTicks > 80 && deathTicks % 10 == 0) {

            level().explode(
                    this,
                    getX(),
                    getY(),
                    getZ(),
                    1.5F,
                    Level.ExplosionInteraction.NONE
            );
        }

        //Loot burst
        if (deathTicks == 155) {
            scatterBossLoot();
        }

        //Removal
        if (deathTicks >= 160) {
            addsprite();
            this.remove(RemovalReason.KILLED);
            this.gameEvent(GameEvent.ENTITY_DIE);

        }
    }

    private void addsprite(){
        SpriteEntity entity = ModEntities.SPRITE.get().create(level());
        entity.setVariant(getSpriteVariant());
        entity.setPos(this.getX(), this.getY() + 1, this.getZ());
        level().addFreshEntity(entity);
    }

    private void scatterBossLoot() {
        int dustAmount = random.nextInt(24, 42);
        scatterDust(dustAmount);
    }

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
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @Nonnull DamageSource source) {
        return false;
    }

    @Override
    public boolean canBeLeashed(Player player) {
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
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    public abstract SoundEvent getSummonSound();

    public abstract Component getFeySummonMessage();

    public abstract SimpleParticleType getParticle();

    public abstract SpriteEntity.SpriteVariant getSpriteVariant();

}
