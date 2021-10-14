package com.feywild.feywild.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;

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
    
    public static IFutureReloadListener createReloadListener() {
        return new ReloadListener<Void>() {
            @Nonnull
            @Override
            protected Void prepare(@Nonnull IResourceManager rm, @Nonnull IProfiler profiler) {
                return null;
            }

            @Override
            protected void apply(@Nonnull Void value, @Nonnull IResourceManager rm, @Nonnull IProfiler profiler) {
                books = DatapackHelper.loadStackList(rm, "feywild_util", "books");
            }
        };
    }
}
