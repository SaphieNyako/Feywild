package com.feywild.feywild.network;

import com.feywild.feywild.block.entity.FeyAltarBlockEntity;

import com.feywild.feywild.setup.ClientProxy;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ItemMessage {
    public ItemStack item;
    public BlockPos pos;
    public int index;

    //Read msg from buf
    public ItemMessage(PacketBuffer buf){
        item = buf.readItemStack();
        pos = buf.readBlockPos();
        index = buf.readInt();
    }

    //Save msg to buf
    public void toBytes(PacketBuffer buf){
        buf.writeItemStack(item);
        buf.writeBlockPos(pos);
        buf.writeInt(index);
    }

    //constructor
    public ItemMessage(ItemStack item, BlockPos pos, int index){
        this.item = item;
        this.pos = pos;
        this.index = index;
    }

    //handle package data
    public void handle(Supplier<NetworkEvent.Context> ctx){

        World world = new ClientProxy().getClientWorld();

        ctx.get().enqueueWork(()-> {
            //Set the items for the client !!! be careful to give it the right location

                    if(world.getTileEntity(pos) instanceof FeyAltarBlockEntity ) {
                        FeyAltarBlockEntity tile = (FeyAltarBlockEntity) world.getTileEntity(pos);
                        tile.setInventorySlotContents(index,item);

                    }

        });
        ctx.get().setPacketHandled(true);
    }
}
