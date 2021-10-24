package com.feywild.feywild.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;

import javax.annotation.Nonnull;
import java.util.List;

public class LibraryBooks {
    
    private static List<ItemStack> books = ImmutableList.of();

    public static List<ItemStack> getLibraryBooks() {
        return books;
    }
    
    public static ItemStack getBook(int idx) {
        if (idx < 0 || idx >= books.size()) {
            return ItemStack.EMPTY;
        } else {
            return books.get(idx).copy();
        }
    }
    
    public static PreparableReloadListener createReloadListener() {
        return new SimplePreparableReloadListener<Void>() {
            @Nonnull
            @Override
            protected Void prepare(@Nonnull ResourceManager rm, @Nonnull ProfilerFiller profiler) {
                return null;
            }

            @Override
            protected void apply(@Nonnull Void value, @Nonnull ResourceManager rm, @Nonnull ProfilerFiller profiler) {
                books = DatapackHelper.loadStackList(rm, "feywild_util", "books");
            }
        };
    }
}
