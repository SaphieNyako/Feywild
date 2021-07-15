package com.feywild.feywild.container;

import com.feywild.feywild.block.entity.mana.CapabilityMana;
import com.feywild.feywild.block.entity.mana.CustomManaStorage;
import com.feywild.feywild.block.entity.mana.IManaStorage;
import com.feywild.feywild.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

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
                addSlot(new SlotItemHandler(h, 0, 30, 56)); //this will hold the feydust
                addSlot(new SlotItemHandler(h, 1, 30, 8)); // this will hold the scheme
                addSlot(new SlotItemHandler(h, 2, 82, 15)); // item 1
                addSlot(new SlotItemHandler(h, 3, 55, 29)); // item 2
                addSlot(new SlotItemHandler(h, 4, 112, 29)); // item 3
                addSlot(new SlotItemHandler(h, 5, 70, 50)); // item 4
                addSlot(new SlotItemHandler(h, 6, 96, 50)); // item 5
                addSlot(new SlotItemHandler(h, 7, 149, 56)); // Output
            });
        }

        layoutPlayerInventorySlots(8, 84); //position on image for player inventory.
        trackMana();
    }

    @Override
    public boolean stillValid(@Nonnull PlayerEntity playerIn) {
        return true;
    }

    /* QUOTE: What the hell is going on? */

    private void trackMana() {
        addDataSlot(new IntReferenceHolder() {

            @Override
            public int get() {
                return getMana() & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityMana.MANA).ifPresent(h -> {
                    int manaStored = h.getManaStored() & 0xffff0000;
                    ((CustomManaStorage) h).setMana(manaStored + (value & 0xffff));
                });
            }
        });

        addDataSlot(new IntReferenceHolder() {

            @Override
            public int get() {
                return (getMana() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tileEntity.getCapability(CapabilityMana.MANA).ifPresent(h -> {
                    int manaStored = h.getManaStored() & 0x0000ffff;
                    ((CustomManaStorage) h).setMana(manaStored | (value << 16));
                });
            }
        });
    }

    public int getMana() {
        return tileEntity.getCapability(CapabilityMana.MANA).map(IManaStorage::getManaStored).orElse(0);
    }

    //transferStackInSlot - This makes it possible to put in items....
    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull PlayerEntity playerIn, int index) {

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            if (index == 0) {

                if (!this.moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack, itemstack);

            } else {

                if (stack.getItem() == ModItems.FEY_DUST.get()) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }

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
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }

        return index;
    }

    // TODO These should go in an abstract class
    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }

        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
