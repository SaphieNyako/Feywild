package com.saphienyako.feywild.entity.base;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


@OnlyIn(Dist.CLIENT)
public class TreeEntCameraHandler {
    //TODO does this work?

    @SubscribeEvent
    public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.player.getVehicle() instanceof TreeEntBase) {
            event.setPitch(event.getPitch() - 3.0F);
            event.setRoll(event.getRoll() + 3.5F);
        }
    }

    @SubscribeEvent
    public static void onCameraFov(ViewportEvent.ComputeFov event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.player.getVehicle() instanceof TreeEntBase) {
            event.setFOV(event.getFOV() + 18.0F);
        }
    }
}

