package com.feywild.feywild.entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.*;
import net.minecraft.entity.player.PlayerEntity;
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

import javax.annotation.Nullable;

public class TraderEntity extends AbstractVillagerEntity implements IReputationTracking {

    private static final DataParameter<VillagerData> DATA_VILLAGER_DATA = EntityDataManager.defineId(VillagerEntity.class, DataSerializers.VILLAGER_DATA);
    public Int2ObjectMap<VillagerTrades.ITrade[]> dwarvenTrades = new Int2ObjectOpenHashMap<>();
    private int villagerXp;
    private PlayerEntity lastTradedPlayer;
    private int updateMerchantTimer;
    private boolean increaseProfessionLevelOnUpdate;
    private int villagerLevel;

    public TraderEntity(EntityType<? extends AbstractVillagerEntity> entity, World world) {
        super(entity, world);
        this.setVillagerData(this.getVillagerData().setType(VillagerType.PLAINS).setProfession(VillagerProfession.NONE));

    }

    protected void rewardTradeXp(MerchantOffer p_213713_1_) {
        int i = 3 + this.random.nextInt(4);
        this.villagerXp += p_213713_1_.getXp();
        this.lastTradedPlayer = this.getTradingPlayer();
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

        VillagerTrades.ITrade[] dwarvenTradeList = DwarvenTrades.DWARVEN_TRADES.get(1);
        setVillagerLevel(1);
        if (dwarvenTradeList != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addOffersFromItemListings(merchantoffers, dwarvenTradeList, 2);
        }

    }

    protected void updateTradesAgain(int number) {

        VillagerTrades.ITrade[] dwarvenTradeList = DwarvenTrades.DWARVEN_TRADES.get(number);

        if (dwarvenTradeList != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addOffersFromItemListings(merchantoffers, dwarvenTradeList, 2);
        }

    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        //compound.putBoolean("tamed", tamed);
        compound.putInt("Xp", this.villagerXp);
        compound.putInt("Level", this.getVillagerLevel());

    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        //this.levelInt = compound.getInt("level");
        if (compound.contains("Xp", 3)) {
            this.villagerXp = compound.getInt("Xp");
        }
        this.villagerLevel = compound.getInt("Level");
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.TOOLSMITH, 1));
    }

    private boolean shouldIncreaseLevel() {
        int i = this.getVillagerData().getLevel();
        return VillagerData.canLevelUp(i) && this.villagerXp >= VillagerData.getMaxXpPerLevel(i);
    }

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

    public int getVillagerXp() {
        return this.villagerXp;
    }

    public void setVillagerXp(int p_213761_1_) {
        this.villagerXp = p_213761_1_;
    }

    @Override
    public void onReputationEventFrom(IReputationType p_213739_1_, Entity p_213739_2_) {
        //gossips added normally
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
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

}
