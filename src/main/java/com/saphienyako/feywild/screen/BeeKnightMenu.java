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
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class BeeKnightMenu extends AbstractContainerMenu {


    private final Container beeKnightContainer;
    public BeeMountEntity beeMount;

    // With Help from https://github.com/Mrbysco/ChocoCraft4/tree/arch/1.21
    // Under MIT LICENSE
    public static BeeKnightMenu create(int i, Inventory inventory, RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        UUID uuid = registryFriendlyByteBuf.readUUID();
        List<BeeMountEntity> beeMountEntities = inventory.player.level().getEntitiesOfClass(BeeMountEntity.class,
                inventory.player.getBoundingBox().inflate(16), test -> test.getUUID().equals(uuid));
        BeeMountEntity beeMountEntity = beeMountEntities.isEmpty() ? null :beeMountEntities.getFirst();
        return new BeeKnightMenu(i, inventory, new SimpleContainer(28), beeMountEntity);
    }


    public BeeKnightMenu(int containerId, Inventory inventory, Container beeKnightContainer, final BeeMountEntity beeMount) {
        super(ModMenuTypes.BEE_KNIGHT_MENU.get(), containerId);
        this.beeKnightContainer = beeKnightContainer;
        this.beeMount = beeMount;
        beeKnightContainer.startOpen(inventory.player);
        //HORSE_ARMOR
        this.addSlot(new Slot(beeKnightContainer, 0, 80, 45) { //slot, x, y
            @Override
            public boolean mayPlace(@Nonnull ItemStack stack) {
                return stack.is(Items.DIAMOND_HORSE_ARMOR)
                        || stack.is(Items.IRON_HORSE_ARMOR)
                        || stack.is(Items.GOLDEN_HORSE_ARMOR);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        //CHEST_PLATE
        this.addSlot(new Slot(beeKnightContainer, 1, 116, 45) {
            @Override
            public boolean mayPlace(@Nonnull ItemStack stack) {
                return stack.is(Items.DIAMOND_CHESTPLATE)
                        || stack.is(Items.NETHERITE_CHESTPLATE)
                        || stack.is(Items.IRON_CHESTPLATE)
                        || stack.is(Items.GOLDEN_CHESTPLATE);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        //LANCE_SLOT
        this.addSlot(new Slot(beeKnightContainer, 2, 98, 45){
            @Override
            public boolean mayPlace(@Nonnull ItemStack stack) {

                return stack.is(Items.DIAMOND_SWORD)
                        || stack.is(Items.NETHERITE_SWORD)
                        || stack.is(Items.IRON_SWORD)
                        || stack.is(Items.GOLDEN_SWORD);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });


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
        return  this.beeKnightContainer.stillValid(player)
                && this.beeMount.isAlive()
                && player.distanceToSqr(this.beeMount) < 64;


       /* return this.beeMount.isAlive()
                && player.distanceToSqr(this.beeMount) < 64; */
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.beeKnightContainer.stopOpen(player);
    }

}
