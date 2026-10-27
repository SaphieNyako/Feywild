package com.saphienyako.feywild.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nonnull;
import java.util.List;

public class ToolTipBaseItem extends Item {
    protected final MutableComponent component;
    public ToolTipBaseItem(Properties pProperties, MutableComponent component) {
        super(pProperties);
        this.component = component;
    }

    public Component getTooltipComponent() {
        return component;
    }
}
