package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class BellsnickelMenu extends AbstractContainerMenu {

    private static final int PLAYER_INVENTORY_ROWS = 3;
    private static final int PLAYER_INVENTORY_COLUMNS = 9;
    private static final int HOT_BAR_SLOT_COUNT = 9;
    private static final int BELLSNICKEL_INVENTORY_SIZE = 28;
    private static final int BELLSNICKEL_STORAGE_ROWS = 2;
    private static final int BELLSNICKEL_STORAGE_COLUMNS = 9;

    private static final int BOOK_SLOT = 0;
    private static final int FEY_INK_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int COPY_BOOK_SLOT = 3;
    private static final int STORAGE_START_SLOT = 4;
    private final Container bellsnickelContainer;
    public BellsnickelEntity bellsnickel;

    // With Help from https://github.com/Mrbysco/ChocoCraft4/tree/arch/1.21
    // Under MIT LICENSE
    public static BellsnickelMenu create(int i, Inventory inventory, RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        UUID uuid = registryFriendlyByteBuf.readUUID();
        List<BellsnickelEntity> bellsnickels = inventory.player.level().getEntitiesOfClass(
                BellsnickelEntity.class,
                inventory.player.getBoundingBox().inflate(16),
                entity -> entity.getUUID().equals(uuid)
        );
        BellsnickelEntity bellsnickelEntity = bellsnickels.isEmpty() ? null :bellsnickels.getFirst();
        return new BellsnickelMenu(i, inventory, new SimpleContainer(BELLSNICKEL_INVENTORY_SIZE), bellsnickelEntity);
    }


    public BellsnickelMenu(int containerId, Inventory inventory, Container bellsnickelContainer, final BellsnickelEntity bellsnickel) {
        super(ModMenuTypes.BELLSNICKEL_MENU.get(), containerId);
        this.bellsnickelContainer = bellsnickelContainer;
        this.bellsnickel = bellsnickel;
        bellsnickelContainer.startOpen(inventory.player);

        this.addBookSlot();
        this.addFeyInkSlot();
        this.addOutputSlot();
        this.addCopyBookSlot();
        this.addBellsnickelInventory();
        this.addPlayerInventory(inventory);
        this.addPlayerHotBar(inventory);
    }

    private void addBookSlot() {
        this.addSlot(new Slot(this.bellsnickelContainer, BOOK_SLOT, 26, 78) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(Items.BOOK);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    private void addFeyInkSlot() {
        this.addSlot(new Slot(this.bellsnickelContainer, FEY_INK_SLOT, 48, 78) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(ModItems.FEY_INK_BOTTLE);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    private void addOutputSlot() {
        this.addSlot(new Slot(this.bellsnickelContainer, OUTPUT_SLOT, 98, 78) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }

            @Override
            public void set(@NotNull ItemStack stack) {
                super.set(stack);
                this.setChanged();
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    private void addCopyBookSlot() {
        this.addSlot(new Slot(this.bellsnickelContainer, COPY_BOOK_SLOT, 170, 78) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(Items.ENCHANTED_BOOK);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    private void addBellsnickelInventory() {
        int startX = 26;
        int startY = 23;
        int slotIndex = STORAGE_START_SLOT;

        for (int row = 0; row < BELLSNICKEL_STORAGE_ROWS; row++) {
            for (int column = 0; column < BELLSNICKEL_STORAGE_COLUMNS; column++) {
                this.addSlot(new Slot(this.bellsnickelContainer, slotIndex++, startX + column * 18, startY + row * 18));
            }
        }
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int row = 0; row < PLAYER_INVENTORY_ROWS; row++) {
            for (int column = 0; column < PLAYER_INVENTORY_COLUMNS; column++) {
                this.addSlot(new Slot(inventory, column + row * PLAYER_INVENTORY_COLUMNS + HOT_BAR_SLOT_COUNT, 26 + column * 18, 117 + row * 18 + 18));
            }
        }
    }

    private void addPlayerHotBar(Inventory inventory) {
        for (int column = 0; column < HOT_BAR_SLOT_COUNT; column++) {
            this.addSlot(new Slot(inventory, column, 26 + column * 18, 157));
        }
    }

    // TODO: Check if quick-move uses the correct slot positions. 22 top 28??

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
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
                } else if (index < j) {
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
    public boolean stillValid(@NotNull Player player) {
        return this.bellsnickel.isAlive() && player.distanceToSqr(this.bellsnickel) < 64;
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.bellsnickelContainer.stopOpen(player);
    }
}


