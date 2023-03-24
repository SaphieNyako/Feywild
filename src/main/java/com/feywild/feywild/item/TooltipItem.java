package com.feywild.feywild.item;

import com.feywild.feywild.util.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.moddingx.libx.base.ItemBase;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TooltipItem extends ItemBase {

    private final Component[] itemTooltip;

    public TooltipItem(ModX mod, Properties properties, Component... itemTooltip) {
        super(mod, properties);
        this.itemTooltip = itemTooltip;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) {
            TooltipHelper.addTooltip(tooltip, level, this.itemTooltip);
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
