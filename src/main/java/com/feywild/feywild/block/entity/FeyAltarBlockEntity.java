package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FeyAltarBlockEntity extends TileEntity {
    //inventory limit
    private final int size = 5;

    //inventory handler
    private final LazyOptional<ItemStackHandler> itemHandler = LazyOptional.of(this::createHandler);

    public FeyAltarBlockEntity() {
        super(ModBlocks.FEY_ALTAR_ENTITY.get());
    }


    //Read data on world init
    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        CompoundNBT invTag = nbt.getCompound("inv");
        itemHandler.ifPresent(itemStackHandler -> ((INBTSerializable<CompoundNBT>)itemStackHandler).deserializeNBT(invTag));
        super.read(state, nbt);
    }

    //init
    public ItemStackHandler createHandler() {
        return new ItemStackHandler(5);
    }



    //Save data on world close
    @Override
    public CompoundNBT write(CompoundNBT compound) {
       itemHandler.ifPresent(itemStackHandler -> {
        CompoundNBT compoundNBT = ((INBTSerializable<CompoundNBT>) itemStackHandler).serializeNBT();
        compound.put("inv",compoundNBT);
        });
        return super.write(compound);
    }

    //Add Inventory capability
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }
}
