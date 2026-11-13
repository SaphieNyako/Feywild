package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.entity.BeeMountEntity;
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

public class BeeKnightMenu extends AbstractContainerMenu {

    private static final int PLAYER_INVENTORY_ROWS = 3;
    private static final int PLAYER_INVENTORY_COLUMNS = 9;
    private static final int HOT_BAR_SLOT_COUNT = 9;

    private static final int BEE_KNIGHT_INVENTORY_SIZE = 28;

    private static final int HORSE_ARMOR_SLOT = 0;
    private static final int CHEST_PLATE_SLOT = 1;
    private static final int SPEAR_SLOT = 2;
    private final Container beeKnightContainer;
    public BeeMountEntity beeMount;

    // With Help from https://github.com/Mrbysco/ChocoCraft4/tree/arch/1.21
    // Under MIT LICENSE
    public static BeeKnightMenu create(int i, Inventory inventory, RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        UUID uuid = registryFriendlyByteBuf.readUUID();
        List<BeeMountEntity> beeMountEntities = inventory.player.level().getEntitiesOfClass(
                BeeMountEntity.class,
                inventory.player.getBoundingBox().inflate(16),
                entity -> entity.getUUID().equals(uuid)
        );
        BeeMountEntity beeMountEntity = beeMountEntities.isEmpty() ? null :beeMountEntities.getFirst();
        return new BeeKnightMenu(i, inventory, new SimpleContainer(BEE_KNIGHT_INVENTORY_SIZE), beeMountEntity);
    }


    public BeeKnightMenu(int containerId, Inventory inventory, Container beeKnightContainer, final BeeMountEntity beeMount) {
        super(ModMenuTypes.BEE_KNIGHT_MENU.get(), containerId);
        this.beeKnightContainer = beeKnightContainer;
        this.beeMount = beeMount;
        beeKnightContainer.startOpen(inventory.player);

        this.addHorseArmorSlot();
        this.addChestPlateSlot();
        this.addLanceSlot();
        this.addPlayerInventory(inventory);
        this.addPlayerHotBar(inventory);
    }

    private void addHorseArmorSlot() {
        this.addSlot(new Slot(this.beeKnightContainer, HORSE_ARMOR_SLOT, 80, 45) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(Items.DIAMOND_HORSE_ARMOR) || stack.is(Items.IRON_HORSE_ARMOR) || stack.is(Items.GOLDEN_HORSE_ARMOR)|| stack.is(Items.NETHERITE_HORSE_ARMOR);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    private void addChestPlateSlot() {
        this.addSlot(new Slot(this.beeKnightContainer, CHEST_PLATE_SLOT, 116, 45) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(Items.DIAMOND_CHESTPLATE) || stack.is(Items.NETHERITE_CHESTPLATE) || stack.is(Items.IRON_CHESTPLATE) || stack.is(Items.GOLDEN_CHESTPLATE);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    private void addLanceSlot() {
        this.addSlot(new Slot(this.beeKnightContainer, SPEAR_SLOT, 98, 45) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(Items.DIAMOND_SPEAR) || stack.is(Items.NETHERITE_SPEAR) || stack.is(Items.IRON_SPEAR) || stack.is(Items.GOLDEN_SPEAR);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int row = 0; row < PLAYER_INVENTORY_ROWS; row++) {
            for (int column = 0; column < PLAYER_INVENTORY_COLUMNS; column++) {
                this.addSlot(new Slot(inventory, column + row * PLAYER_INVENTORY_COLUMNS + HOT_BAR_SLOT_COUNT, 26 + column * 18, 117 + row * 18 - 18));
            }
        }
    }

    private void addPlayerHotBar(Inventory inventory) {
        for (int column = 0; column < HOT_BAR_SLOT_COUNT; column++) {
            this.addSlot(new Slot(inventory, column, 26 + column * 18, 157));
        }
    }


    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int i = this.beeKnightContainer.getContainerSize() + 1; //why plus 1??
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
        return  this.beeKnightContainer.stillValid(player) && this.beeMount.isAlive() && player.distanceToSqr(this.beeMount) < 64;
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.beeKnightContainer.stopOpen(player);
    }

}
