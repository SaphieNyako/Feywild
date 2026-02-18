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

        if (mc.player != null && mc.player.getVehicle() instanceof TreeEntBase treeEnt) {
            // Get current raw distance
            float current = event.getDistance();
            // Increase for extra zoom‑out
            event.setDistance(current + 8.0f);
        }
    }
}

