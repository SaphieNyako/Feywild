package com.feywild.feywild.trade.item;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.List;

// Stack factory that picks randomly from a list of stack factories
public final class CompoundStackFactory implements StackFactory {

    public final List<StackFactory> factories;

    public CompoundStackFactory(List<StackFactory> factories) {
        this.factories = List.copyOf(factories);
        if (this.factories.isEmpty()) throw new IllegalStateException("Can't create empty compound stack factory");
    }

    @Override
    public ItemStack createStack(RandomSource random) {
        return this.factories.get(random.nextInt(this.factories.size())).createStack(random);
    }
}
