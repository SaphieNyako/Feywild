package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ItemMessage;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class FeyAltarBlockEntity extends TileEntity implements ITickableTileEntity {
    //inventory limit
    private final int size = 5;
    private boolean shouldload = true;
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


    public boolean shouldRender(){
        AtomicBoolean ret = new AtomicBoolean(false);
        itemHandler.ifPresent(itemStackHandler -> {
             ret.set(!itemStackHandler.getStackInSlot(0).isEmpty());
        });
        return ret.get();
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


    //This is a bad way of doing it but for some reason I can't figure it out so ¯\_(ツ)_/¯
    @Override
    public void tick() {
        if(!world.isRemote&&shouldload){
            itemHandler.ifPresent(itemStackHandler -> {
                CompoundNBT compoundNBT = ((INBTSerializable<CompoundNBT>) itemStackHandler).serializeNBT();
                FeywildPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new ItemMessage(compoundNBT,pos));
            });
            shouldload = true;
        }
    }
}