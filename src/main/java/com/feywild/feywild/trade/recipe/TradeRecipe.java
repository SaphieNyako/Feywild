package com.feywild.feywild.trade.recipe;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

// For use in JEI. Contains all trades for one level
public class TradeRecipe {
    
    public final ResourceLocation id;
    public final int level;
    public final List<Entry> trades;

    public TradeRecipe(ResourceLocation id, int level, List<Entry> trades) {
        this.id = id;
        this.level = level;
        this.trades = ImmutableList.copyOf(trades);
    }
    
    public static class Entry {

        public final List<ItemStack> input;
        public final List<ItemStack> additional;
        public final List<ItemStack> output;

        public Entry(List<ItemStack> input, List<ItemStack> additional, List<ItemStack> output) {
            this.input = copyList(input);
            this.additional = copyList(additional);
            this.output = copyList(output);
        }

        private static List<ItemStack> copyList(List<ItemStack> stacks) {
            //noinspection UnstableApiUsage
            return stacks.stream()
                    .filter(s -> !s.isEmpty())
                    .map(ItemStack::copy)
                    .collect(ImmutableList.toImmutableList());
        }
    }
}
