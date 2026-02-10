package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class BellsnickelMenu extends AbstractContainerMenu {

    private final Container bellsnickelContainer;
    public BellsnickelEntity bellsnickel;

    // With Help from https://github.com/Mrbysco/ChocoCraft4/tree/arch/1.21
    // Under MIT LICENSE
    public static BellsnickelMenu create(int i, Inventory inventory, FriendlyByteBuf buf) {
        UUID uuid = buf.readUUID();
        List<BellsnickelEntity> bellsnickels = inventory.player.level().getEntitiesOfClass(BellsnickelEntity.class,
                inventory.player.getBoundingBox().inflate(16), test -> test.getUUID().equals(uuid));
        BellsnickelEntity bellsnickelEntity =bellsnickels.isEmpty() ? null :bellsnickels.get(0);
        return new BellsnickelMenu(i, inventory, new SimpleContainer(28), bellsnickelEntity);
    }


    public BellsnickelMenu(int containerId, Inventory inventory, Container bellsnickelContainer, final BellsnickelEntity bellsnickel) {
        super(ModMenuTypes.BELLSNICKEL_MENU.get(), containerId);
        this.bellsnickelContainer = bellsnickelContainer;
        this.bellsnickel = bellsnickel;
        bellsnickelContainer.startOpen(inventory.player);
        //BOOK_SLOT
        this.addSlot(new Slot(bellsnickelContainer, 0, 26, 78) { //slot, x, y
            @Override
            public boolean mayPlace(@Nonnull ItemStack stack) {
                return stack.is(Items.BOOK);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        //FEY_INK_SLOT
        this.addSlot(new Slot(bellsnickelContainer, 1, 48, 78) {
            @Override
            public boolean mayPlace(@Nonnull ItemStack stack) {
                return stack.is(ModItems.FEY_INK_BOTTLE.get());
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        //OUTPUT
        this.addSlot(new Slot(bellsnickelContainer, 2, 98, 78){
            @Override
            public boolean mayPlace(@Nonnull ItemStack stack) {
                return false;
            }

            @Override
            public void set(@Nonnull ItemStack stack) {
                super.set(stack); // allow server to set output
                this.setChanged(); // make sure it updates
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        //COPY ENCHANTED BOOK
        this.addSlot(new Slot(bellsnickelContainer, 3, 170, 78) {
            @Override
            public boolean mayPlace(@Nonnull ItemStack stack) {
                return stack.is(Items.ENCHANTED_BOOK);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        // Bellsnickel storage inventory (2 rows of 9)
        int startX = 26; //7
        int startY = 23; //7
        int slotIndex = 4; // start after book/ink/output/copy

        for (int row = 0; row < 2; row++) {
            for (int colum = 0; colum < 9; colum++) {
                this.addSlot(new Slot(bellsnickelContainer, slotIndex++, startX + colum * 18, startY + row * 18));
            }
        }
        //Add inventory player //8 25- startX  102 (82?)  99- startY
        for (int row = 0; row < 3; row++) {
            for (int colum = 0; colum < 9; colum++) {
                this.addSlot(new Slot(inventory, colum + row * 9 + 9, 26 + colum * 18, 117 + row * 18 + -18));
            }
        }
        //Add toolbar player 8- 26 142- 156
        for (int colum = 0; colum < 9; colum++) {
            this.addSlot(new Slot(inventory, colum, 26 + colum * 18, 157));
        }

    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int i = this.bellsnickelContainer.getContainerSize() + 1; //why plus 1??
            if (index < i) {
                if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).mayPlace(itemstack1) && !this.getSlot(1).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).mayPlace(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 1 || !this.moveItemStackTo(itemstack1, 2, i, false)) {
                int j = i + 27;
                int k = j + 9;
                if (index >= j && index < k) {
                    if (!this.moveItemStackTo(itemstack1, i, j, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= i && index < j) {
                    if (!this.moveItemStackTo(itemstack1, j, k, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, j, j, false)) {
                    return ItemStack.EMPTY;
                }

                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
       /* return !this.bellsnickel.hasInventoryChanged(this.bellsnickelContainer)
                && this.bellsnickelContainer.stillValid(player)
                && this.bellsnickel.isAlive()
                && player.distanceToSqr(this.bellsnickel) < 64;

        */
        return this.bellsnickel.isAlive()
                && player.distanceToSqr(this.bellsnickel) < 64;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.bellsnickelContainer.stopOpen(player);
    }
}


