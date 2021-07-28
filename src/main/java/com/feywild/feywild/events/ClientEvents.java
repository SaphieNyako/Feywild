package com.feywild.feywild.events;

import com.feywild.feywild.util.ClientUtil;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {
    @SubscribeEvent
    public void onGuiOpened(GuiOpenEvent event) {
        ClientUtil.openMainMenuScreen(event);
    }
}
