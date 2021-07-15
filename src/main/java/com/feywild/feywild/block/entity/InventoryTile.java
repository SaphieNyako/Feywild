package com.feywild.feywild.block.entity;

import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ItemMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.List;

// TODO either remove or make it use an IItemHandlerModifiable that is acessible directly
public abstract class InventoryTile extends TileEntity implements IInventory {

    public InventoryTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    // Currently copies the stacks into a list on each call. Inefficient and redundant.
    public List<ItemStack> getItems() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < getContainerSize(); i++) {
            if (!getItem(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    //get stack
    @Nonnull
    @Override
    public ItemStack getItem(int index) {
        return getItems().get(index);
    }

    //decrement
    @Nonnull
    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack stack = getItem(index);
        stack.shrink(count);
        return stack;
    }

    /* Update the inventory
       flags = -1  means update the entire inventory
       otherwise update one item slot
     */
    // TODO flags is named badly as these are no flags bu ta slot index
    public void updateInventory(int flags, boolean shouldCraft) {
        if (flags == -1) {
            for (int i = 0; i < getContainerSize(); i++) {
                // TODO Don't ever send something position related to all players
                // Maybe copy request / update of tile entities from LibX for this
                FeywildPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new ItemMessage(getItems().get(i), worldPosition, i));
            }
        } else {
            FeywildPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new ItemMessage(getItems().get(flags), worldPosition, flags));
        }
    }

    // Clear slot
    @Nonnull
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return getItems().set(index, ItemStack.EMPTY);
    }

    //Set stack at index
    @Override
    public void setItem(int index, @Nonnull ItemStack stack) {
        getItems().set(index, stack);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return player.distanceToSqr(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()) <= 16;
    }

    //Clear all items
    @Override
    public void clearContent() {
        for (int i = 0; i < getContainerSize(); i++) {
            removeItemNoUpdate(i);
        }
        updateInventory(-1, false);
    }
}
