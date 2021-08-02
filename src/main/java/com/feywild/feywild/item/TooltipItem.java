package com.feywild.feywild.item;

import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class TooltipItem extends ItemBase {

    private final ITextComponent[] itemTooltip;
    
    public TooltipItem(ModX mod, Properties properties, ITextComponent... itemTooltip) {
        super(mod, properties);
        this.itemTooltip = itemTooltip;
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, this.itemTooltip);
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
