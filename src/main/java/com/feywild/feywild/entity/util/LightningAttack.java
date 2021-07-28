package com.feywild.feywild.entity.util;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class LightningAttack extends Entity {

    public long seed;
    private int life;
    private int flashes;
    private boolean visualOnly;
    @Nullable
    private ServerPlayerEntity cause;

    public LightningAttack(EntityType<? extends LightningBoltEntity> p_i231491_1_, World p_i231491_2_) {
        super(p_i231491_1_, p_i231491_2_);
        this.noCulling = true;
        this.life = 2;
        this.seed = this.random.nextLong();
        this.flashes = this.random.nextInt(3) + 1;
    }

    public void setVisualOnly(boolean p_233623_1_) {
        this.visualOnly = p_233623_1_;
    }

    @Nonnull
    @Override
    public SoundCategory getSoundSource() {
        return SoundCategory.NEUTRAL;
    }

    public void setCause(@Nullable ServerPlayerEntity p_204809_1_) {
        this.cause = p_204809_1_;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.life == 2) {

            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.TRIDENT_THUNDER, SoundCategory.NEUTRAL, 10000.0F, 0.8F + this.random.nextFloat() * 0.2F);
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.TRIDENT_RETURN, SoundCategory.NEUTRAL, 2.0F, 0.5F + this.random.nextFloat() * 0.2F);
        }

        --this.life;
        if (this.life < 0) {
            if (this.flashes == 0) {
                this.remove();
            } else if (this.life < -this.random.nextInt(10)) {
                --this.flashes;
                this.life = 1;
                this.seed = this.random.nextLong();

            }
        }

        if (this.life >= 0) {
            if (!(this.level instanceof ServerWorld)) {
                this.level.setSkyFlashTime(2);
            } else if (!this.visualOnly) {
                double d0 = 3.0D;
                List<Entity> list = this.level.getEntities(this, new AxisAlignedBB(this.getX() - 3.0D, this.getY() - 3.0D, this.getZ() - 3.0D, this.getX() + 3.0D, this.getY() + 6.0D + 3.0D, this.getZ() + 3.0D), Entity::isAlive);

                if (this.cause != null) {
                    CriteriaTriggers.CHANNELED_LIGHTNING.trigger(this.cause, list);
                }
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(double p_70112_1_) {
        double d0 = 64.0D * getViewScale();
        return p_70112_1_ < d0 * d0;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(@Nonnull CompoundNBT p_70037_1_) {
    }

    @Override
    protected void addAdditionalSaveData(@Nonnull CompoundNBT p_213281_1_) {
    }

    @Nonnull
    @Override
    public IPacket<?> getAddEntityPacket() {
        return new SSpawnObjectPacket(this);
    }
}
