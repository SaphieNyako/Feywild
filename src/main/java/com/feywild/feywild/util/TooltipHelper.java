package com.feywild.feywild.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;

public class TooltipHelper {

    public static void addTooltip(List<Component> tooltip, Level level, Component... lines) {
        if (level.isClientSide && KeyboardHelper.isHoldingShift()) {
            tooltip.addAll(Arrays.asList(lines));
        } else {
            Component cmp = Component.translatable("message.feywild.itemmessage", Component.keybind("key.sneak")).withStyle(ChatFormatting.GRAY);
            if (!tooltip.contains(cmp)) {
                tooltip.add(cmp);
            }
        }
    }
}
