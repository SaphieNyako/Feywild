package com.feywild.feywild.block.entity.mana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class CustomManaStorage extends ManaStorage implements INBTSerializable<CompoundNBT> {

    public CustomManaStorage(int capacity, int maxTransfer) {
        super(capacity, 0, maxTransfer);
    }

    protected void onManaChanged() {}

    public void setMana(int mana) {

        this.mana = mana;
        onManaChanged();
    }

    public void generateMana(int mana) {
        this.mana += mana;
        if (this.mana > getMaxManaStored()) {
            this.mana = getManaStored();
        }
        onManaChanged();
    }

    public void consumeMana(int mana) {
        this.mana -= mana;
        if (this.mana < 0) {
            this.mana = 0;
        }
        onManaChanged();
    }

    @Override
    public int extractMana(int maxExtract, boolean simulate) {
        return super.extractMana(maxExtract, simulate);
    }

    @Override
    public int getManaStored() {
        return super.getManaStored();
    }

    @Override
    public int getMaxManaStored() {
        return super.getMaxManaStored();
    }

    @Override
    public boolean canExtract() {
        return super.canExtract();
    }

    @Override
    public boolean canReceive() {
        return super.canReceive();
    }

    public boolean isFullMana() { return getManaStored() >= getMaxManaStored(); }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("mana", getManaStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

        setMana(nbt.getInt("mana"));

    }
}
