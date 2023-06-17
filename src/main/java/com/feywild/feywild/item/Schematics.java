package com.feywild.feywild.item;

import net.minecraft.network.chat.Component;
import org.moddingx.libx.mod.ModX;

import net.minecraft.world.item.Item.Properties;

public class Schematics extends TooltipItem {

    public Schematics(ModX mod, Properties properties, Component... itemTooltip) {
        super(mod, properties, itemTooltip);
    }
}
