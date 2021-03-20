package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ItemMessage;
import com.feywild.feywild.network.ParticleMessage;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class FeyAltarBlockEntity extends TileEntity implements ITickableTileEntity {
    private final int size = 5;
    private boolean shouldload = true;
    private int count = 0, limit;
    Random random = new Random();
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

    //gets called every tick
    @Override
    public void tick() {
        if(world.isRemote) return;
        count++;
        if(shouldload){
            // initilize limit and loop through all items to sync them with the client
            limit = random.nextInt(20*6);
            itemHandler.ifPresent(itemStackHandler -> {
                CompoundNBT compoundNBT = ((INBTSerializable<CompoundNBT>) itemStackHandler).serializeNBT();
                FeywildPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new ItemMessage(compoundNBT,pos));
            });
            shouldload = true;
        }
        //summon particles randomly (did this here bc for some reason random ticks are killing me today)
        if(count > limit){
            limit = random.nextInt(20*6);
            if(random.nextDouble() > 0.5) {
                // send packet to player to summon particles
                FeywildPacketHandler.sendToPlayersInRange(world, pos, new ParticleMessage(pos.getX()+ random.nextDouble(),pos.getY()+ 0.3+ random.nextDouble(), pos.getZ()+ random.nextDouble(), 0, 0, 0, 1), 32);
            }
            count = 0;
        }
    }
}