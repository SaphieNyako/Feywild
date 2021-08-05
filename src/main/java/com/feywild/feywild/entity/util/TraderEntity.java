package com.feywild.feywild.entity.util;

import com.feywild.feywild.trade.TradeManager;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TraderEntity extends AbstractVillagerEntity implements IReputationTracking {

    private int villagerXp;
    private int updateMerchantTimer;
    private boolean increaseProfessionLevelOnUpdate;

    private long lastRestockGameTime;
    private int numberOfRestocksToday;
    private long lastRestockCheckDayTime;
    
    private VillagerData villagerData;

    public TraderEntity(EntityType<? extends AbstractVillagerEntity> entity, World world) {
        super(entity, world);
        this.villagerData = new VillagerData(VillagerType.PLAINS, VillagerProfession.TOOLSMITH, 1);
    }
    
    public abstract String getTradeCategory();

    @Override
    protected void rewardTradeXp(MerchantOffer offer) {
        int i = 3 + this.random.nextInt(4);
        this.villagerXp += offer.getXp();
        if (this.shouldIncreaseLevel()) {
            this.updateMerchantTimer = 40;
            this.increaseProfessionLevelOnUpdate = true;
            i += 5;
        }

        if (offer.shouldRewardExp()) {
            this.level.addFreshEntity(new ExperienceOrbEntity(this.level, this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }
    }

    @Override
    protected void updateTrades() {
        this.offers = new MerchantOffers();
        this.villagerData = TradeManager.getTrades(this.getType(), this.getTradeCategory()).initialize(this, this.villagerData, this.offers, this.random);
    }

    protected void levelUp() {
        this.villagerData = TradeManager.getTrades(this.getType(), this.getTradeCategory()).levelUp(this, this.villagerData, this.offers, this.random);
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("VillagerLevel", this.villagerData.getLevel());
        nbt.putInt("VillagerXp", this.villagerXp);
        nbt.putLong("LastRestock", this.lastRestockGameTime);
        nbt.putInt("RestocksToday", this.numberOfRestocksToday);

    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.villagerData = new VillagerData(VillagerType.PLAINS, VillagerProfession.TOOLSMITH, nbt.contains("VillagerLevel") ? nbt.getInt("VillagerLevel") : 1);
        if (nbt.contains("VillagerXp", Constants.NBT.TAG_ANY_NUMERIC)) {
            this.villagerXp = nbt.getInt("VillagerXp");
        }
        this.lastRestockGameTime = nbt.getLong("LastRestock");
        this.numberOfRestocksToday = nbt.getInt("RestocksToday");
    }

    private boolean shouldIncreaseLevel() {
        int i = this.getVillagerData().getLevel();
        return VillagerData.canLevelUp(i) && this.villagerXp >= VillagerData.getMaxXpPerLevel(i);
    }

    @Override
    protected void customServerAiStep() {
        if (!this.isTrading() && this.updateMerchantTimer > 0) {
            --this.updateMerchantTimer;
            if (this.updateMerchantTimer <= 0) {
                if (this.increaseProfessionLevelOnUpdate) {
                    this.levelUp();
                    this.increaseProfessionLevelOnUpdate = false;
                }
                this.addEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
            }
        }
        super.customServerAiStep();
    }

    public VillagerData getVillagerData() {
        return this.villagerData;
    }

    @Override
    public int getVillagerXp() {
        return this.villagerXp;
    }
    
    @Override
    public void onReputationEventFrom(@Nonnull IReputationType p_213739_1_, @Nonnull Entity p_213739_2_) {
        //
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(@Nonnull ServerWorld p_241840_1_, @Nonnull AgeableEntity p_241840_2_) {
        return null;
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public boolean canRestock() {
        return true;
    }

    public void restock() {
        this.updateDemand();

        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetUses();
        }

        this.lastRestockGameTime = this.level.getGameTime();
        this.numberOfRestocksToday++;
    }

    private boolean needsToRestock() {
        for (MerchantOffer merchantoffer : this.getOffers()) {
            if (merchantoffer.needsRestock()) {
                return true;
            }
        }
        return false;
    }

    private boolean allowedToRestock() {
        return this.numberOfRestocksToday == 0 || (this.numberOfRestocksToday < 2 && this.level.getGameTime() > this.lastRestockGameTime + 2400);
    }

    public boolean shouldRestock() {
        boolean timeForRestock = this.level.getGameTime() > this.lastRestockGameTime + 12000L;
        if (this.lastRestockCheckDayTime > 0 && !timeForRestock) {
            long lastRestockDay = this.lastRestockCheckDayTime / 24000;
            long currentRestockDay = this.level.getDayTime() / 24000;
            if (currentRestockDay > lastRestockDay) timeForRestock = true;
        }

        this.lastRestockCheckDayTime = this.level.getDayTime();
        if (timeForRestock) {
            this.lastRestockGameTime = this.level.getGameTime();
            this.resetNumberOfRestocks();
        }

        return this.allowedToRestock() && this.needsToRestock();
    }

    private void catchUpDemand() {
        int restocksLeft = 2 - this.numberOfRestocksToday;
        if (restocksLeft > 0) {
            for (MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.resetUses();
            }
        }
        
        for (int j = 0; j < restocksLeft; ++j) {
            this.updateDemand();
        }
    }

    private void updateDemand() {
        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.updateDemand();
        }
    }

    private void resetNumberOfRestocks() {
        this.catchUpDemand();
        this.numberOfRestocksToday = 0;
    }
}
