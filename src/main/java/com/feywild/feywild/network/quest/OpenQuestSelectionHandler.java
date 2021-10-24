package com.feywild.feywild.network.quest;

import com.feywild.feywild.screens.SelectQuestScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenQuestSelectionHandler {

    public static void handle(OpenQuestSelectionSerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> Minecraft.getInstance().setScreen(new SelectQuestScreen(msg.title, msg.alignment, msg.quests)));
        context.get().setPacketHandled(true);
    }
}
