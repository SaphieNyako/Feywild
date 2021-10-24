package com.feywild.feywild.trade.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Random;

// Stack factory that picks randomly from a list of stack factories
public class CompoundStackFactory implements StackFactory {

    public final List<StackFactory> factories;

    public CompoundStackFactory(List<StackFactory> factories) {
        this.factories = ImmutableList.copyOf(factories);
        if (this.factories.isEmpty()) throw new IllegalStateException("Can't create empty compound stack factory");
    }

    @Override
    public ItemStack createStack(Random random) {
        return this.factories.get(random.nextInt(this.factories.size())).createStack(random);
    }
}
