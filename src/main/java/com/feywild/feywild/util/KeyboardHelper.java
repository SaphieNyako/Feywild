package com.feywild.feywild.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

// TODO use actual keybinds here instead of raw hardcoded calls to InputMappings
// For now we want the player to press shift. Its not bound to the sneak.
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
