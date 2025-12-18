package com.saphienyako.feywild.block.renderer;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class ClientTickHandler {
    private static int ticksInGame = 0;
    public static int ticksInGame() {
        return ticksInGame;
    }
    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        ticksInGame++;
    }
}
