package com.feywild.feywild.network;

import com.feywild.feywild.screens.LibrarianScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenLibraryScreenHandler {
    
    public static void handle(OpenLibraryScreenSerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> Minecraft.getInstance().setScreen(new LibrarianScreen(msg.title, msg.books)));
        context.get().setPacketHandled(true);
    }
}
