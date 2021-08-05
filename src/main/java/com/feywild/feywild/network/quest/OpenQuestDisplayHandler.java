package com.feywild.feywild.network.quest;

import com.feywild.feywild.screens.DisplayQuestScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenQuestDisplayHandler {

    public static void handle(OpenQuestDisplaySerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> Minecraft.getInstance().setScreen(new DisplayQuestScreen(msg.display, msg.confirmationButtons)));
        context.get().setPacketHandled(true);
    }
}
