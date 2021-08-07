package com.feywild.feywild.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.List;

public class TooltipHelper {
    
    public static void addTooltip(List<ITextComponent> tooltip, ITextComponent... lines) {
        if (KeyboardHelper.isHoldingShift()) {
            tooltip.addAll(Arrays.asList(lines));
        } else {
            tooltip.add(new TranslationTextComponent("message.feywild.itemmessage", new KeybindTextComponent("key.sneak")));
        }
    }
}
