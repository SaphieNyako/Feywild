package com.saphienyako.feywild.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {

    public static final CreativeModeTab FEYWILD_TAB  = new CreativeModeTab("feywild_tab"){
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.SUMMONING_SCROLL_SPRING_PIXIE.get());
        }
    };
}
