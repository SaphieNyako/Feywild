package com.saphienyako.feywild.entity.base;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.CalculateDetachedCameraDistanceEvent;

@OnlyIn(Dist.CLIENT)
public class TreeEntCameraHandler {
    @SubscribeEvent
    public static void onCalculateCameraDistance(CalculateDetachedCameraDistanceEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null && mc.player.getVehicle() instanceof TreeEntBase) {
            float current = event.getDistance();
            event.setDistance(current + 8.0f);
        }
    }
}

