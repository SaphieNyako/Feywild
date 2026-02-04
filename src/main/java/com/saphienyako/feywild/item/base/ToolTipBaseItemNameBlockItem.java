package com.saphienyako.feywild.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ToolTipBaseItemNameBlockItem extends ItemNameBlockItem {

    protected final MutableComponent component;
    public ToolTipBaseItemNameBlockItem(Block block, Properties properties, MutableComponent component) {
        super(block, properties);
        this.component = component;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) {
            tooltip.add(this.component.withStyle(ChatFormatting.BLUE));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
