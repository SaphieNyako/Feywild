package com.feywild.feywild.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

public class KeyboardHelper {

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingShift() {
        return isKeyDown(Minecraft.getInstance().getWindow().getWindow(), Minecraft.getInstance().options.keyShift.getKey());
    }

    private static boolean isKeyDown(long window, InputMappings.Input key) {
        int value = key.getValue();
        if (value != InputMappings.UNKNOWN.getValue()) {
            try {
                if (key.getType() == InputMappings.Type.KEYSYM) {
                    return InputMappings.isKeyDown(window, value);
                } else if (key.getType() == InputMappings.Type.MOUSE) {
                    return GLFW.glfwGetMouseButton(window, value) == GLFW.GLFW_PRESS;
                }
            } catch (Exception ignored) {

            }
        }
        return false;
    }


}
