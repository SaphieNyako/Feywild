package com.feywild.feywild.network.quest;

import com.feywild.feywild.screens.DisplayQuestScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenQuestDisplayHandler {

    public static void handle(OpenQuestDisplaySerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (msg.display.sound != null) {
                Player player = Minecraft.getInstance().player;
                if (player != null) {
                    Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(msg.display.sound, SoundSource.MASTER, 1, 1, player.getX(), player.getY(), player.getZ()));
                }
            }
            Minecraft.getInstance().setScreen(new DisplayQuestScreen(msg.display, msg.confirmationButtons));
        });
        context.get().setPacketHandled(true);
    }
}
