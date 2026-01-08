package com.saphienyako.feywild.item;


import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ModCreativeModeTab {

    public static final ItemGroup FEYWILD_TAB  = new  ItemGroup("feywild_tab"){

        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get());
        }
    };
}
