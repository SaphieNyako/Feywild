package com.feywild.feywild.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class LibraryBell extends TileEntity {

    private int annoyance = 0;
    private int despawnTimer = 0;

    @Nullable
    private UUID player = null;

    @Nullable
    private UUID librarian = null;

    @Nullable
    private UUID security = null;

    public LibraryBell(TileEntityType<?> type) {
        super(type);
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
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        this.annoyance = nbt.getInt("annoyance");
        this.player = nbt.hasUUID("playerId") ? nbt.getUUID("playerId") : null;
        this.librarian = nbt.hasUUID("librarianId") ? nbt.getUUID("librarianId") : null;
        this.security = nbt.hasUUID("securityId") ? nbt.getUUID("securityId") : null;
        this.despawnTimer = nbt.getInt("despawner");
    }

    @Nonnull
    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putInt("annoyance", this.annoyance);
        nbt.putInt("despawner", this.despawnTimer);
        if (this.player == null) {
            nbt.remove("playerId");
        } else {
            nbt.putUUID("playerId", this.player);
        }
        if (this.librarian == null) {
            nbt.remove("librarianId");
        } else {
            nbt.putUUID("librarianId", this.librarian);
        }
        if (this.security == null) {
            nbt.remove("securityId");
        } else {
            nbt.putUUID("securityId", this.security);
        }

        return super.save(nbt);
    }
}
