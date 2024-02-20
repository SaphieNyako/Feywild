package com.feywild.feywild.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class LibraryBell extends BlockEntity {

    private int annoyance = 0;
    private int despawnTimer = 0;

    @Nullable
    private UUID player = null;

    @Nullable
    private UUID librarian = null;

    @Nullable
    private UUID security = null;

    public LibraryBell(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    public int getAnnoyance() {
        return this.annoyance;
    }

    public void setAnnoyance(int annoyance) {
        this.annoyance = annoyance;
    }

    @Nullable
    public UUID getPlayer() {
        return this.player;
    }

    public void setPlayer(@Nullable UUID player) {
        this.player = player;
    }

    @Nullable
    public UUID getLibrarian() {
        return this.librarian;
    }

    public void setLibrarian(@Nullable UUID librarian) {
        this.librarian = librarian;
    }

    @Nullable
    public UUID getSecurity() {
        return this.security;
    }

    public void setSecurity(@Nullable UUID security) {
        this.security = security;
    }

    public int getDespawnTimer() {
        return despawnTimer;
    }

    public void setDespawnTimer(int despawnTimer) {
        this.despawnTimer = despawnTimer;
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        this.annoyance = nbt.getInt("Annoyance");
        this.player = nbt.hasUUID("Player") ? nbt.getUUID("Player") : null;
        this.librarian = nbt.hasUUID("Librarian") ? nbt.getUUID("Librarian") : null;
        this.security = nbt.hasUUID("Security") ? nbt.getUUID("Security") : null;
        this.despawnTimer = nbt.getInt("DespawnTimer");
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("Annoyance", this.annoyance);
        nbt.putInt("DespawnTimer", this.despawnTimer);
        if (this.player == null) {
            nbt.remove("Player");
        } else {
            nbt.putUUID("Player", this.player);
        }
        if (this.librarian == null) {
            nbt.remove("Librarian");
        } else {
            nbt.putUUID("Librarian", this.librarian);
        }
        if (this.security == null) {
            nbt.remove("Security");
        } else {
            nbt.putUUID("Security", this.security);
        }
    }
}
