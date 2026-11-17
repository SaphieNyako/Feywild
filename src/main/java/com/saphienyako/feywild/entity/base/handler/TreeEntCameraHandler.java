package com.saphienyako.feywild.entity.base.handler;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CalculateDetachedCameraDistanceEvent;

@EventBusSubscriber(modid = Feywild.MOD_ID, value = Dist.CLIENT)
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

