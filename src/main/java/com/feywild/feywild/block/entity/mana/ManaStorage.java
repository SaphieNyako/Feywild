package com.feywild.feywild.block.entity.mana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public class ManaStorage implements IManaStorage, INBTSerializable<CompoundNBT> {

    protected final int capacity;
    protected final int maxReceive;
    protected final int maxExtract;
    protected int mana;
    @Nullable
    protected Runnable manaChanged;

    public ManaStorage(int capacity) {
        this(capacity, Integer.MAX_VALUE);
    }

    public ManaStorage(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer);
    }

    public ManaStorage(int capacity, int maxReceive, int maxExtract) {
        this(capacity, maxReceive, maxExtract, null);
    }

    public ManaStorage(int capacity, @Nullable Runnable manaChange) {
        this(capacity, Integer.MAX_VALUE);
    }

    public ManaStorage(int capacity, int maxTransfer, @Nullable Runnable manaChange) {
        this(capacity, maxTransfer, maxTransfer);
    }

    public ManaStorage(int capacity, int maxReceive, int maxExtract, @Nullable Runnable manaChange) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.mana = 0;
    }

    @Override
    public int receiveMana(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;
        int manaReceived = Math.min(capacity - mana, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            mana += manaReceived;
        return manaReceived;
    }

    @Override
    public int extractMana(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;
        int manaExtracted = Math.min(mana, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            mana -= manaExtracted;
        return manaExtracted;
    }

    @Override
    public int getMana() {
        return MathHelper.clamp(mana, 0, capacity);
    }

    public void setMana(int mana) {
        this.mana = mana;
        if (this.manaChanged != null) this.manaChanged.run();
    }

    @Override
    public int getMaxMana() {
        return capacity;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("mana", getMana());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setMana(nbt.getInt("mana"));
    }
}
