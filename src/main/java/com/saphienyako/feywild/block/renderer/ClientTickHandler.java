package com.saphienyako.feywild.block.renderer;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class ClientTickHandler {
    private static int ticksInGame = 0;

    public static int ticksInGame() {
        return ticksInGame;
    }


    //TODO check does this still work?
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        // This is equivalent to the old START phase
        ticksInGame++;
    }
}
