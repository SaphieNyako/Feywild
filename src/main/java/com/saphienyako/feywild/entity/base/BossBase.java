package com.saphienyako.feywild.entity.base;

import com.saphienyako.feywild.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;


import javax.annotation.Nonnull;

public abstract class BossBase extends Monster {

    public final ServerBossEvent bossInfo;
    protected BossBase(EntityType<? extends Monster> entity, Level level, ServerBossEvent bossInfo) {
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

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {

        super.dropCustomDeathLoot(level, source, recentlyHit);
        scatterItem(new ItemStack(ModItems.PIXIE_WING_TIARA.get()));
        scatterDust(random.nextInt(8, 16));
    }

    private void scatterItem(ItemStack stack) {

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

    private void scatterDust(int totalAmount) {

        int remaining = totalAmount;

        while (remaining > 0) {

            int stackSize = Math.min(
                    remaining,
                    random.nextInt(2, 6)
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
    public boolean canChangeDimensions(@Nonnull Level level,@Nonnull Level level_) {
        return false;
    }

    @Override
    public boolean ignoreExplosion(@Nonnull Explosion explosion) {
        return true;
    }

    public abstract SoundEvent getSummonSound();

    public abstract Component getFeySummonMessage();
}
