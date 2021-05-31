package com.feywild.feywild.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class TraderEntity extends AbstractVillagerEntity implements IMerchant {

    @Nullable
    private PlayerEntity tradingPlayer; //get set
    private MerchantOffers offers;
    private Set<UUID> tradedCustomers = new HashSet<>();

    @Nullable
    protected TraderEntity(EntityType<? extends AbstractVillagerEntity> p_i48575_1_, World p_i48575_2_) {
        super(p_i48575_1_, p_i48575_2_);
    }

    @Nullable
    @Override
    public PlayerEntity getTradingPlayer() {
        return this.tradingPlayer;
    }

    @Override
    public void setTradingPlayer(@Nullable PlayerEntity player) {
        this.tradingPlayer = player;
    }

    //check for tradingPlayer
    public boolean hasTradingPlayer() {
        return this.tradingPlayer != null;
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            this.getTradersData();
        }
        return this.offers;
    }

    protected abstract void getTradersData();

    //setClientSideOffers
    @Override
    public void overrideOffers(@Nullable MerchantOffers offers) {

    }

    //on Trade
    @Override
    public void notifyTrade(MerchantOffer offer) {

        offer.increaseUses();
        if (this.tradingPlayer != null) {
            this.tradedCustomers.add(this.tradingPlayer.getUUID());
        }
    }

    //Verify Selling Item
    @Override
    public void notifyTradeUpdated(ItemStack stack) {

    }

    @Override
    public World getLevel() {
        return this.level;
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int p_213702_1_) {

    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.VILLAGER_YES;
    }
}
