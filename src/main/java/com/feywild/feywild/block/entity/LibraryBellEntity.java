package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.server.ServerWorld;

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

    public void setLibrarian(Entity librarian) {
        this.librarian = librarian;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void setAnnoyance(int annoyance) {
        this.annoyance = annoyance;
    }

    public void setSecurity(Entity security) {
        this.security = security;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public Entity getLibrarian() {
        return librarian;
    }

    public Entity getSecurity() {
        return security;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        annoyance = nbt.getInt("annoyance");
        playerEntity = level.getPlayerByUUID(nbt.getUUID("playerId"));
        librarian = level instanceof ServerWorld ? ((ServerWorld) level).getEntity(nbt.getUUID("librarianId")) : null;
        security = level instanceof ServerWorld ? ((ServerWorld) level).getEntity(nbt.getUUID("securityId")) : null;
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("annoyance",annoyance);
        compound.putUUID("playerId", playerEntity.getUUID());
        compound.putUUID("librarianId", librarian.getUUID());
        compound.putUUID("securityId", security.getUUID());
        return super.save(compound);
    }
}
