package com.feywild.feywild.block.entity.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class ManaStorage {

    private final int capacity;
    private int mana;
    @Nullable private final Runnable manaChanged;

    public ManaStorage(int capacity) {
        this(capacity, null);
    }
    
    public ManaStorage(int capacity, @Nullable Runnable manaChanged) {
        this.capacity= capacity;
        this.manaChanged = manaChanged;
    }

    public int receiveMana(int maxReceive, boolean simulate) {
        int manaReceived = Math.min(this.capacity - this.mana, maxReceive);
        if (!simulate) this.mana += manaReceived;
        return manaReceived;
    }

    public int extractMana(int maxExtract, boolean simulate) {
        int manaExtracted = Math.min(this.mana, maxExtract);
        if (!simulate) this.mana -= manaExtracted;
        return manaExtracted;
    }

    public int getMana() {
        return Mth.clamp(this.mana, 0, this.capacity);
    }

    public void setMana(int mana) {
        this.mana = mana;
        if (this.manaChanged != null) this.manaChanged.run();
    }

    public int getMaxMana() {
        return this.capacity;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("mana", this.getMana());
        return tag;
    }

    public void load(CompoundTag nbt) {
        this.setMana(nbt.getInt("mana"));
    }
}
