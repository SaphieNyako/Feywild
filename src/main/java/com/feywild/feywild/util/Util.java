package com.feywild.feywild.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.inventory.DataSlot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Util {

    public static DataSlot trackHigherOrderBits(Supplier<Integer> getter, Consumer<Integer> setter) {
        return new DataSlot() {

            @Override
            public int get() {
                return (getter.get() >>> 16) & 0xFFFF;
            }

            @Override
            public void set(int value) {
                setter.accept((getter.get() & 0xFFFF) | (value << 16));
            }
        };
    }
    
    public static DataSlot trackLowerOrderBits(Supplier<Integer> getter, Consumer<Integer> setter) {
        return new DataSlot() {
            
            @Override
            public int get() {
                return getter.get() & 0xFFFF;
            }

            @Override
            public void set(int value) {
                setter.accept((getter.get() & 0xFFFF0000) | (value & 0xFFFF));
            }
        };
    }
    
    public static boolean simpleMatch(List<Ingredient> ingredients, List<ItemStack> stacks) {
        if (ingredients.size() != stacks.size()) return false;
        List<ItemStack> left = new ArrayList<>(stacks);
        ingredients: for (Ingredient ingredient : ingredients) {
            Iterator<ItemStack> itr = left.iterator();
            while (itr.hasNext()) {
                if (ingredient.test(itr.next())) {
                    itr.remove();
                    continue ingredients;
                }
            }
            return false;
        }
        return true;
    }
}
