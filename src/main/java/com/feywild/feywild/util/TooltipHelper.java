package com.feywild.feywild.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.KeybindComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Arrays;
import java.util.List;

public class TooltipHelper {
    
    public static void addTooltip(List<Component> tooltip, Component... lines) {
        if (KeyboardHelper.isHoldingShift()) {
            tooltip.addAll(Arrays.asList(lines));
        } else {
            TranslatableComponent textComponent = (TranslatableComponent) new TranslatableComponent("message.feywild.itemmessage", new KeybindComponent("key.sneak")).withStyle(ChatFormatting.GRAY);
            if(!tooltip.contains(textComponent))
                tooltip.add(textComponent);
        }
    }
}
