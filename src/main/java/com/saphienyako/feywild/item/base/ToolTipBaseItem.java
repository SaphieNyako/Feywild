package com.saphienyako.feywild.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ToolTipBaseItem extends Item {
    protected final MutableComponent component;
    public ToolTipBaseItem(Properties pProperties, MutableComponent component) {
        super(pProperties);
        this.component = component;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack,@Nonnull TooltipContext context,@Nonnull List<Component> tooltip,@Nonnull TooltipFlag flag) {
        tooltip.add(this.component.withStyle(ChatFormatting.BLUE));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
