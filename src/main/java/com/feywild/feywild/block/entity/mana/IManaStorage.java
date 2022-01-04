package com.feywild.feywild.block.entity.mana;

public interface IManaStorage {

    /**
     * Adds mana to the storage. Returns quantity of mana that was accepted.
     *
     * @param maxReceive Maximum amount of mana to be inserted.
     * @param simulate   If {@code true}, the insertion will only be simulated.
     * @return Amount of mana that was (or would have been, if simulated) accepted by the storage.
     */
    int receiveMana(int maxReceive, boolean simulate);

    /**
     * Removes mana from the storage. Returns quantity of mana that was removed.
     *
     * @param maxExtract Maximum amount of mana to be extracted.
     * @param simulate   If {@code true}, the extraction will only be simulated.
     * @return Amount of mana that was (or would have been, if simulated) extracted from the storage.
     */
    int extractMana(int maxExtract, boolean simulate);

    /**
     * Returns the amount of mana currently stored.
     */
    int getMana();

    /**
     * Returns the maximum amount of mana that can be stored.
     */
    int getMaxMana();

    /**
     * Used to determine if this storage can receive mana.
     * If this is false, then any calls to receiveMana will return 0.
     */
    boolean canReceive();

    /**
     * Returns if this storage can have mana extracted.
     * If this is false, then any calls to extractMana will return 0.
     */
    boolean canExtract();
}
