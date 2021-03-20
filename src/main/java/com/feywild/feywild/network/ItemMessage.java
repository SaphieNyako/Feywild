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
    public List<ItemStack> items;
    public BlockPos pos;

    //Read msg from buf
    public ItemMessage(PacketBuffer buf){
        items = Lists.newLinkedList();
        for (int i = 0; i < buf.readInt(); i++) {
            items.add(buf.readItemStack());
        }
        pos = buf.readBlockPos();
    }

    //Save msg to buf
    public void toBytes(PacketBuffer buf){
        buf.writeInt(items.size());
        for (ItemStack item : items) {
            buf.writeItemStack(item);
        }
        buf.writeBlockPos(pos);
    }

    //constructor
    public ItemMessage(List<ItemStack> items, BlockPos pos){
        this.items = items;
        this.pos = pos;
    }

    //handle package data
    public void handle(Supplier<NetworkEvent.Context> ctx){

        World world = new ClientProxy().getClientWorld();

        ctx.get().enqueueWork(()-> {
            //Set the items for the client !!! be careful to give it the right location

                    if(world.getTileEntity(pos) instanceof FeyAltarBlockEntity && items.size() > 0) {
                        FeyAltarBlockEntity tile = (FeyAltarBlockEntity) world.getTileEntity(pos);

                        for (int i = 0; i < items.size(); i++) {
                            tile.setInventorySlotContents(i, items.get(i));
                        }
                    }

        });
        ctx.get().setPacketHandled(true);
    }
}
