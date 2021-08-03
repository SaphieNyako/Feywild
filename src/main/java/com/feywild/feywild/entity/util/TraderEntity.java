package com.feywild.feywild.entity.util;

import com.feywild.feywild.entity.util.trades.DwarvenTrades;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.*;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TraderEntity extends AbstractVillagerEntity implements IReputationTracking {

    private static final DataParameter<VillagerData> DATA_VILLAGER_DATA = EntityDataManager.defineId(VillagerEntity.class, DataSerializers.VILLAGER_DATA);
    private int merchantXp;
    private int updateMerchantTimer;
    private boolean increaseProfessionLevelOnUpdate;
    private int villagerLevel;
    protected boolean isTamed;

    private long lastRestockGameTime;
    private int numberOfRestocksToday;
    private long lastRestockCheckDayTime;

    public TraderEntity(EntityType<? extends AbstractVillagerEntity> entity, World world) {
        super(entity, world);
    }

    @Override
    protected void rewardTradeXp(MerchantOffer p_213713_1_) {
        int i = 3 + this.random.nextInt(4);
        this.merchantXp += p_213713_1_.getXp();
        if (this.shouldIncreaseLevel()) {
            this.updateMerchantTimer = 40;
            this.increaseProfessionLevelOnUpdate = true;
            i += 5;
        }

        if (p_213713_1_.shouldRewardExp()) {
            this.level.addFreshEntity(new ExperienceOrbEntity(this.level, this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }

    }

    @Override
    protected void updateTrades() {

        if (isTamed) {
            VillagerTrades.ITrade[] dwarvenTradeList = DwarvenTrades.DWARVEN_BLACKSMITH_TRADES.get(1);
            setVillagerLevel(1);
            if (dwarvenTradeList != null) {
                MerchantOffers merchantoffers = this.getOffers();
                this.addOffersFromItemListings(merchantoffers, dwarvenTradeList, 4);
            }
        } else {

            VillagerTrades.ITrade[] dwarvenTradeList = DwarvenTrades.DWARVEN_TRADES.get(1);
            setVillagerLevel(1);
            if (dwarvenTradeList != null) {
                MerchantOffers merchantoffers = this.getOffers();
                this.addOffersFromItemListings(merchantoffers, dwarvenTradeList, 2);
            }
        }
    }

    protected void updateTradesAgain(int number) {

        if (isTamed) {
            VillagerTrades.ITrade[] dwarvenTradeList = DwarvenTrades.DWARVEN_BLACKSMITH_TRADES.get(number);
            setVillagerLevel(number);
            if (dwarvenTradeList != null) {
                MerchantOffers merchantoffers = this.getOffers();
                this.addOffersFromItemListings(merchantoffers, dwarvenTradeList, 4);
            }
        } else {

            if (number == 1) {
                VillagerTrades.ITrade[] dwarvenTradeList = DwarvenTrades.DWARVEN_TRADES.get(2);
                setVillagerLevel(2);
                if (dwarvenTradeList != null) {
                    MerchantOffers merchantoffers = this.getOffers();
                    this.addOffersFromItemListings(merchantoffers, dwarvenTradeList, 3);
                }
            } else {

                VillagerTrades.ITrade[] dwarvenTradeList = DwarvenTrades.DWARVEN_TRADES.get(number);
                setVillagerLevel(number);
                if (dwarvenTradeList != null) {
                    MerchantOffers merchantoffers = this.getOffers();
                    this.addOffersFromItemListings(merchantoffers, dwarvenTradeList, 3);
                }
            }
        }
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Level", this.getVillagerLevel());
        compound.putInt("Xp", this.merchantXp);
        compound.putBoolean("tamed", isTamed);

        compound.putLong("LastRestock", this.lastRestockGameTime);
        compound.putInt("RestocksToday", this.numberOfRestocksToday);

    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        //  this.levelInt = compound.getInt("level");
        this.isTamed = compound.getBoolean("tamed");
        if (compound.contains("Xp", 3)) {
            this.merchantXp = compound.getInt("Xp");
        }
        this.villagerLevel = compound.getInt("Level");
        this.lastRestockGameTime = compound.getLong("LastRestock");
        this.numberOfRestocksToday = compound.getInt("RestocksToday");


    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.TOOLSMITH, 1));
    }

    private boolean shouldIncreaseLevel() {
        int i = this.getVillagerData().getLevel();
        return VillagerData.canLevelUp(i) && this.merchantXp >= VillagerData.getMaxXpPerLevel(i);
    }

    @Override
    protected void customServerAiStep() {

        if (!this.isTrading() && this.updateMerchantTimer > 0) {
            --this.updateMerchantTimer;
            if (this.updateMerchantTimer <= 0) {
                if (this.increaseProfessionLevelOnUpdate) {
                    this.increaseMerchantCareer();
                    this.increaseProfessionLevelOnUpdate = false;
                }

                this.addEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
            }
        }
        super.customServerAiStep();
    }

    private void increaseMerchantCareer() {
        this.setVillagerData(this.getVillagerData().setLevel(this.getVillagerData().getLevel() + 1));

        setVillagerLevel(this.villagerLevel + 1);

        this.updateTradesAgain(getVillagerLevel()); //updates with the same....
    }

    public VillagerData getVillagerData() {
        return this.entityData.get(DATA_VILLAGER_DATA);
    }

    public void setVillagerData(VillagerData p_213753_1_) {
        VillagerData villagerdata = this.getVillagerData();
        if (villagerdata.getProfession() != p_213753_1_.getProfession()) {
            this.offers = null;
        }

        this.entityData.set(DATA_VILLAGER_DATA, p_213753_1_);
    }

    @Override
    public int getVillagerXp() {
        return this.merchantXp;
    }

    public void setMerchantXp(int p_213761_1_) {
        this.merchantXp = p_213761_1_;
    }

    @Override
    public void onReputationEventFrom(@Nonnull IReputationType p_213739_1_, @Nonnull Entity p_213739_2_) {
        //gossips added normally
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

    public int getVillagerLevel() {
        return villagerLevel;
    }

    public void setVillagerLevel(int villagerLevel) {
        this.villagerLevel = villagerLevel;
    }

    /*
    public void startSleeping(BlockPos p_213342_1_) {
        super.startSleeping(p_213342_1_);
        this.brain.setMemory(MemoryModuleType.LAST_SLEPT, this.level.getGameTime());
        this.brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        this.brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    public void stopSleeping() {
        super.stopSleeping();
        this.brain.setMemory(MemoryModuleType.LAST_WOKEN, this.level.getGameTime());
    }
*/

    /* RESTOCK */

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
        ++this.numberOfRestocksToday;
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
        return this.numberOfRestocksToday == 0 || this.numberOfRestocksToday < 2 && this.level.getGameTime() > this.lastRestockGameTime + 2400L;
    }

    public boolean shouldRestock() {
        long i = this.lastRestockGameTime + 12000L;
        long j = this.level.getGameTime();
        boolean flag = j > i;
        long k = this.level.getDayTime();
        if (this.lastRestockCheckDayTime > 0L) {
            long l = this.lastRestockCheckDayTime / 24000L;
            long i1 = k / 24000L;
            flag |= i1 > l;
        }

        this.lastRestockCheckDayTime = k;
        if (flag) {
            this.lastRestockGameTime = j;
            this.resetNumberOfRestocks();
        }

        return this.allowedToRestock() && this.needsToRestock();
    }

    private void catchUpDemand() {
        int i = 2 - this.numberOfRestocksToday;
        if (i > 0) {
            for (MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.resetUses();
            }
        }

        for (int j = 0; j < i; ++j) {
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
