package com.saphienyako.feywild.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.List;

public class ToolTipBaseItemNameBlockItem extends BlockItem {

    protected final MutableComponent component;

    public ToolTipBaseItemNameBlockItem(Block block, Properties properties, MutableComponent component) {
        super(block, properties);
        this.component = component;
    }

    public Component getTooltipComponent() {
        return component;
    }
}
