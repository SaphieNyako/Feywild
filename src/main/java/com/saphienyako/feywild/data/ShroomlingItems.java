package com.saphienyako.feywild.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class ShroomlingItems {

    private static List<ItemStack> itemList = ImmutableList.of();

    public static List<ItemStack> shroomlingItems() {
        return itemList;
    }

    public static ItemStack getShroomlingItem(int idx) {
        if (idx < 0 || idx >= itemList.size()) {
            return ItemStack.EMPTY;
        } else {
            return itemList.get(idx).copy();
        }
    }

    public static PreparableReloadListener createReloadListener() {
        return new SimplePreparableReloadListener<Void>() {
            @Nonnull
            @Override
            protected Void prepare(@Nonnull ResourceManager manager, @Nonnull ProfilerFiller profiler) {
                return null;
            }

            @Override
            protected void apply(@Nonnull Void value, @Nonnull ResourceManager manager, @Nonnull ProfilerFiller profiler) {
                itemList = DatapackHelper.loadStackList(manager, "feywild_trades", "shroomling");
            }
        };
    }


}
