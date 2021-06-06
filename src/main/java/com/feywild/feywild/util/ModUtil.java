package com.feywild.feywild.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;

public class ModUtil {

    public static boolean inventoryContainsItem(PlayerInventory inventory, Item item) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            Item inventoryItem = inventory.getItem(i).getItem();

            if (inventory.getItem(i).getItem() == item.asItem()) {
                return true;
            }
        }
        return false;
    }
}
