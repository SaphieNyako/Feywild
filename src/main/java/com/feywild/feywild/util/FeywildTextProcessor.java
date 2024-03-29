package com.feywild.feywild.util;

import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.moddingx.libx.util.game.TextProcessor;

public class FeywildTextProcessor extends TextProcessor {

    public static final FeywildTextProcessor INSTANCE = new FeywildTextProcessor();

    private FeywildTextProcessor() {

    }

    @Override
    public Style customCommand(Style style, String command) {
        if ("spring".equalsIgnoreCase(command)) {
            return style.withColor(TextColor.fromRgb(0x66cc99));
        } else if ("summer".equalsIgnoreCase(command)) {
            return style.withColor(TextColor.fromRgb(0xffcc00));
        } else if ("autumn".equalsIgnoreCase(command)) {
            return style.withColor(TextColor.fromRgb(0xcc3333));
        } else if ("winter".equalsIgnoreCase(command)) {
            return style.withColor(TextColor.fromRgb(0x66ccff));
        } else {
            return super.customCommand(style, command);
        }
    }
}
