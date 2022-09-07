package com.feywild.feywild.events;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.quest.SyncPlayerGuiStatus;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(value = Dist.CLIENT)
public class ClientEvents {

    private static boolean showGui = true;

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void showGui(RenderGuiOverlayEvent.Pre event) {
        if (!getShowGui()) {  //&& event.getType() == RenderGameOverlayEvent.ElementType.ALL
            event.setCanceled(true);
        }
    }

    public static boolean getShowGui() {
        return showGui;
    }

    public static void setShowGui(boolean showGui) {
        ClientEvents.showGui = showGui;
        if (Minecraft.getInstance().player != null)
            FeywildMod.getNetwork().sendToServer(new SyncPlayerGuiStatus(Minecraft.getInstance().player.getUUID(), showGui));
    }

}
