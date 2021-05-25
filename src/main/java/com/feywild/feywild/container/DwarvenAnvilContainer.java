package com.feywild.feywild.container;

import com.feywild.feywild.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class DwarvenAnvilContainer extends Container {

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public DwarvenAnvilContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(ModContainers.DWARVEN_ANVIL_CONTAINER.get(), windowId);
        this.tileEntity = world.getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        if (tileEntity != null) {

            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 29, 56)); //this will hold the feydust
                addSlot(new SlotItemHandler(h, 1, 46, 9)); // this will hold the scheme
                addSlot(new SlotItemHandler(h, 2, 110, 9)); // item 1
                addSlot(new SlotItemHandler(h, 3, 87, 34)); // item 2
                addSlot(new SlotItemHandler(h, 4, 133, 34)); // item 3
                addSlot(new SlotItemHandler(h, 5, 99, 58)); // item 4
                addSlot(new SlotItemHandler(h, 6, 122, 58)); // item 5
            });
        }

        layoutPlayerInventorySlots(8, 83); //position on image for player inventory.
    }

    @Override //canInteractWith
    public boolean stillValid(PlayerEntity playerIn) {
        return true;
        //isWithinUsableDistance not available anymore
    }

    //transferStackInSlot
    @Override  // WE NEED TO MAKE 7 SLOTS
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        //if slot is not null and has item get Item.
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            //if index number is 0
            if (index == 0) {

                //if not moveItemStackTo ??
                //what are these numbers?
                if (!this.moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                //slot.setChanged();

                slot.onQuickCraft(stack, itemstack);

            } else {

                if (stack.getItem() == ModItems.FEY_DUST.get()) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }

                    //???
                } else if (index < 28) {
                    if (!this.moveItemStackTo(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }

    /* ADD PLAYER INVENTORY */

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {

        for (int i = 0; i > amount; i++) {

            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horizontalAmount, int dx,
                           int verticalAmount, int dy) {

        for (int j = 0; j < verticalAmount; j++) {
            index = addSlotRange(handler, index, x, y, horizontalAmount, dx);
        }

        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {

        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58; //???
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
