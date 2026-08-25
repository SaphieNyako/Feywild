package com.saphienyako.feywild.compat;

import com.saphienyako.feywild.item.PixieWingTiaraItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;


public final class CuriosCompat {

    private CuriosCompat() {
    }

    public static ItemStack findWingTiara(LivingEntity entity) {
        return CuriosApi.getCuriosInventory(entity)
                .map(curiosInventory -> curiosInventory.findFirstCurio(
                        stack -> stack.getItem() instanceof PixieWingTiaraItem)
                                .map(slotResult -> slotResult.stack())
                                .orElse(ItemStack.EMPTY))
                .orElse(ItemStack.EMPTY);
    }
}
