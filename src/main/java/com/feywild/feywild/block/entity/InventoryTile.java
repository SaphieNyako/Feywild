package com.feywild.feywild.block.entity;

import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ItemMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class InventoryTile extends TileEntity implements IInventory {

    public InventoryTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public int getSizeInventory() {
        return 5;
    }

    public List<ItemStack> getItems() {
        return null;
    }

    // is the entire inventory empty?
    @Override
    public boolean isEmpty() {
        for(int i = 0; i < getSizeInventory(); i++){
            if(!getStackInSlot(i).isEmpty()){
                return true;
            }
        }
        return false;
    }
    //get stack
    @Override
    public ItemStack getStackInSlot(int index) {
        return getItems().get(index);
    }

    //decrement
    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = getStackInSlot(index);
        stack.shrink(count);
        return stack;
    }

    /* Update the inventory
       flags = -1  means update the entire inventory
       otherwise update one item slot
     */
    public void updateInventory(int flags) { if(flags == -1){
           for (int i = 0; i < getSizeInventory(); i++) {
               FeywildPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new ItemMessage(getItems().get(i), pos, i));
           }
        }else{
            FeywildPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new ItemMessage(getItems().get(flags),pos,flags));
        }
    }

    // Clear slot
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return getItems().set(index, ItemStack.EMPTY);
    }

    //Set stack at index
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        getItems().set(index,stack);
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return player.getDistanceSq(getPos().getX(), getPos().getY(), getPos().getZ()) <= 16;
    }

    //Clear all items
    @Override
    public void clear() {
        for (int i = 0; i < getSizeInventory(); i++) {
            removeStackFromSlot(i);
        }
        updateInventory(-2);
    }
}
