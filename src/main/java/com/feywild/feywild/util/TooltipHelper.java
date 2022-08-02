package com.feywild.feywild.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;

public class TooltipHelper {

    public static void addTooltip(List<Component> tooltip, Component... lines) {
        if (KeyboardHelper.isHoldingShift()) {
            tooltip.addAll(Arrays.asList(lines));
        } else {
            Component cmp = Component.translatable("message.feywild.itemmessage", Component.keybind("key.sneak")).withStyle(ChatFormatting.GRAY);
            if (!tooltip.contains(cmp)) {
                tooltip.add(cmp);
            }
        }
    }
}
