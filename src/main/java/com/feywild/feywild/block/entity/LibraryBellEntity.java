package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

public class LibraryBellEntity extends TileEntity {

    private int annoyance = 0;
    private PlayerEntity playerEntity = null;
    private Entity librarian = null;
    private Entity security = null;

    public LibraryBellEntity() {
        super(ModBlocks.LIBRARY_BELL_ENTITY.get());
    }

    public int getAnnoyance() {
        return annoyance;
    }

    public void setAnnoyance(int annoyance) {
        this.annoyance = annoyance;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public Entity getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Entity librarian) {
        this.librarian = librarian;
    }

    public Entity getSecurity() {
        return security;
    }

    public void setSecurity(Entity security) {
        this.security = security;
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        annoyance = nbt.getInt("annoyance");
        playerEntity = level == null ? null : level.getPlayerByUUID(nbt.getUUID("playerId"));
    }

    @Nonnull
    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("annoyance", annoyance);
        compound.putUUID("playerId", playerEntity.getUUID());
        return super.save(compound);
    }
}
