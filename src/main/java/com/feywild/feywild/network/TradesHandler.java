package com.feywild.feywild.network;

import com.feywild.feywild.jei.util.FeywildJeiReloader;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TradesHandler {

    public static void handle(TradesSerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (ModList.get().isLoaded("jei")) {
                FeywildJeiReloader.clientTrades = msg.recipes;
                FeywildJeiReloader.reload();
            }
        });
        context.get().setPacketHandled(true);
    }
}
