package com.feywild.feywild.item;

import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.network.chat.Component;

import net.minecraft.world.item.Item.Properties;

public class Schematics extends TooltipItem {
    
    public Schematics(ModX mod, Properties properties, Component... itemTooltip) {
        super(mod, properties, itemTooltip);
    }
}
