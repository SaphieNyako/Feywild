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
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FeywildRecipes {

    //Inefficient item match method
    public static boolean matches(List<Ingredient> ingredients, IInventory inv){
        List<ItemStack> items = new LinkedList<>();

        //Annoying loop added because minecraft's IInventory doesn't have a getItems method
        for(int i =0; i < inv.getSizeInventory(); i++){
            items.add(inv.getStackInSlot(i));
        }

        AtomicInteger progress = new AtomicInteger(0);
        ingredients.forEach(ingredient -> {
            //Check if item matches
            boolean currentItem;
            for(ItemStack item : items){
                currentItem = ingredient.test(item);
                if(currentItem){
                    progress.set(progress.incrementAndGet());
                    break;
                }
            }
        });

        //DEBUG LINE, PENDING REMOVAL
        System.out.println(progress.get());

        return progress.get() >= ingredients.size();
    }
}
