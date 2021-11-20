package com.feywild.feywild.util;

import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.MandragoraEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Util {

    public static IntReferenceHolder trackHigherOrderBits(Supplier<Integer> getter, Consumer<Integer> setter) {
        return new IntReferenceHolder() {

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
    
    public static IntReferenceHolder trackLowerOrderBits(Supplier<Integer> getter, Consumer<Integer> setter) {
        return new IntReferenceHolder() {
            
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

    public static MandragoraEntity getModEntityType(World world) {
        switch (world.random.nextInt(5)) {
            case 1:
                return ModEntityTypes.onionMandragora.create(world);
            case 2:
                return ModEntityTypes.potatoMandragora.create(world);
            case 3:
                return ModEntityTypes.pumpkinMandragora.create(world);
            case 4:
                return ModEntityTypes.tomatoMandragora.create(world);
            case 0:
            default:
                return ModEntityTypes.melonMandragora.create(world);
        }
    }
}
