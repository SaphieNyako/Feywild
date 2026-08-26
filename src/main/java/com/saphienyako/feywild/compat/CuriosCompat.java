package com.saphienyako.feywild.compat;

import com.saphienyako.feywild.item.PixieWingTiaraItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;


public final class CuriosCompat {

    private CuriosCompat() {
    }

    public static ItemStack findWingTiara(LivingEntity entity) {
        return entity.getCapability(CuriosCapability.INVENTORY).map(curiosInventory -> {
                    ICurioStacksHandler headHandler = curiosInventory.getCurios().get("head");

                    if (headHandler == null) {
                        return ItemStack.EMPTY;
                    }

                    IItemHandler stacks = headHandler.getStacks();

                    for (int i = 0; i < stacks.getSlots(); i++) {
                        ItemStack stack = stacks.getStackInSlot(i);

                        if (stack.getItem() instanceof PixieWingTiaraItem) {
                            return stack;
                        }
                    }

                    return ItemStack.EMPTY;
                })
                .orElse(ItemStack.EMPTY);
    }
}
