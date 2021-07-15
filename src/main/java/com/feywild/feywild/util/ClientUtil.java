package com.feywild.feywild.util;

import com.feywild.feywild.screens.PixieScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;

public class ClientUtil {

    public static void openQuestScreen(int quest, int lines, boolean canSkip) {
        Minecraft.getInstance().setScreen(new PixieScreen(new StringTextComponent("Fey Quest"), quest, lines, canSkip));
    }
}
