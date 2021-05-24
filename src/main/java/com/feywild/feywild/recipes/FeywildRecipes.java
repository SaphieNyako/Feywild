package com.feywild.feywild.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.LinkedList;
import java.util.List;

public class FeywildRecipes {

    //Inefficient item match method
    public static boolean matches(List<Ingredient> ingredients, IInventory inv) {
        //Copy the inventory in a separate place just so that I can remove stuff from it
        List<ItemStack> stacks = new LinkedList<>();
        for (int j = 0; j < inv.getContainerSize(); j++) {
            stacks.add(inv.getItem(j));
        }

        //Item check
        List<Ingredient> copy = new LinkedList<>(ingredients);
        for (Ingredient ingredient : ingredients) {

            for (ItemStack stack : stacks) {
                if (!stack.isEmpty() && ingredient.test(stack)) {
                    copy.remove(ingredient);
                    stacks.remove(stack);
                    break;
                }
            }
        }
        return copy.isEmpty();
    }
}
