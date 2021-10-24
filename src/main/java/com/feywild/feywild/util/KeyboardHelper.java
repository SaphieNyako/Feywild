package com.feywild.feywild.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KeyboardHelper {

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingShift() {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), Minecraft.getInstance().options.keyShift.getKey().getValue());
    }
}
