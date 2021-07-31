package com.feywild.feywild.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class LibraryBellEntity extends TileEntity {

    private int annoyance = 0;
    
    @Nullable
    private UUID player = null;
    
    @Nullable
    private UUID librarian = null;
    
    @Nullable
    private UUID security = null;

    public LibraryBellEntity(TileEntityType<?> type) {
        super(type);
    }


    public int getAnnoyance() {
        return annoyance;
    }

    public void setAnnoyance(int annoyance) {
        this.annoyance = annoyance;
    }

    @Nullable
    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(@Nullable UUID player) {
        this.player = player;
    }

    @Nullable
    public UUID getLibrarian() {
        return librarian;
    }

    public void setLibrarian(@Nullable UUID librarian) {
        this.librarian = librarian;
    }

    @Nullable
    public UUID getSecurity() {
        return security;
    }

    public void setSecurity(@Nullable UUID security) {
        this.security = security;
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        annoyance = nbt.getInt("annoyance");
        player = nbt.hasUUID("playerId") ? nbt.getUUID("playerId") : null;
        librarian = nbt.hasUUID("librarianId") ? nbt.getUUID("librarianId") : null;
        security = nbt.hasUUID("securityId") ? nbt.getUUID("securityId") : null;
    }

    @Nonnull
    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putInt("annoyance", annoyance);
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
