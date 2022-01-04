package com.feywild.feywild.entity.base;

import com.feywild.feywild.trade.TradeManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class Trader extends AbstractVillager implements ReputationEventHandler {

    private int villagerXp;
    private int updateMerchantTimer;
    private boolean increaseProfessionLevelOnUpdate;

    private long lastRestockGameTime;
    private int numberOfRestocksToday;
    private long lastRestockCheckDayTime;

    private VillagerData villagerData;

    public Trader(EntityType<? extends AbstractVillager> entity, Level level) {
        super(entity, level);
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
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.getX(), this.getY() + 0.5D, this.getZ(), i));
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
    public void addAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("VillagerLevel", this.villagerData.getLevel());
        nbt.putInt("VillagerXp", this.villagerXp);
        nbt.putLong("LastRestock", this.lastRestockGameTime);
        nbt.putInt("RestocksToday", this.numberOfRestocksToday);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.villagerData = new VillagerData(VillagerType.PLAINS, VillagerProfession.TOOLSMITH, nbt.contains("VillagerLevel") ? nbt.getInt("VillagerLevel") : 1);
        if (nbt.contains("VillagerXp", Tag.TAG_ANY_NUMERIC)) {
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
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
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
    public void onReputationEventFrom(@Nonnull ReputationEventType p_213739_1_, @Nonnull Entity p_213739_2_) {
        //
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@Nonnull ServerLevel level, @Nonnull AgeableMob other) {
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
