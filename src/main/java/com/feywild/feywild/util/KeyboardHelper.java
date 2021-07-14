package com.feywild.feywild.util;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

// TODO use actual keybinds here instead of raw hardcoded calls to InputMappings
// Need to figure out a way to get the actual key press value fr the sneak key
// even with toggle sneak enabled
public class KeyboardHelper {

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingShift() {

        return InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(),
                GLFW.GLFW_KEY_LEFT_SHIFT);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingCtrl() {

        return InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(),
                GLFW.GLFW_KEY_LEFT_CONTROL);
    }
}
