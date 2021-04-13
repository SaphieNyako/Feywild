package com.feywild.feywild.recipes;

import com.feywild.feywild.block.entity.InventoryTile;
import com.feywild.feywild.events.ModRecipes;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.injection.At;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FeywildRecipes {

    //Inefficient item match method
    public static boolean matches(List<Ingredient> ingredients, IInventory inv){
       //Copy the inventory in a separate place just so that I can remove stuff from it
        List<ItemStack> stacks = new LinkedList<>();
        for (int j = 0; j < inv.getSizeInventory(); j++) {
            stacks.add(inv.getStackInSlot(j));
        }

        //Item check
        List<Ingredient> copy = new LinkedList<>(ingredients);
        for(Ingredient ingredient : ingredients){

            for (ItemStack stack : stacks) {
                if (!stack.isEmpty()&&ingredient.test(stack)) {
                    copy.remove(ingredient);
                    stacks.remove(stack);
                    break;
                }
            }
        }
        return copy.isEmpty();
    }
}
