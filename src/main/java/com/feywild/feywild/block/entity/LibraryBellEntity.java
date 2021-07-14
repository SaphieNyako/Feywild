package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LibraryBellEntity extends TileEntity {

    private int annoyance = 0;
    private PlayerEntity playerEntity = null;
    private VillagerEntity librarian = null;
    private IronGolemEntity security = null;

    public LibraryBellEntity() {
        super(ModBlocks.LIBRARY_BELL_ENTITY.get());
    }

    public int getAnnoyance() {
        return annoyance;
    }

    public void setLibrarian(VillagerEntity librarian) {
        this.librarian = librarian;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public void setAnnoyance(int annoyance) {
        this.annoyance = annoyance;
    }

    public void setSecurity(IronGolemEntity security) {
        this.security = security;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public VillagerEntity getLibrarian() {
        return librarian;
    }

    public IronGolemEntity getSecurity() {
        return security;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        annoyance = nbt.getInt("annoyance");
        playerEntity = level.getPlayerByUUID(nbt.getUUID("player_UUID"));
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("annoyance",annoyance);
        compound.putUUID("player_UUID", playerEntity.getUUID());
        return super.save(compound);
    }
}
