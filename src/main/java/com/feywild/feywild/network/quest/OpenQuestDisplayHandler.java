package com.feywild.feywild.network.quest;

import com.feywild.feywild.screens.DisplayQuestScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenQuestDisplayHandler {

    public static void handle(OpenQuestDisplaySerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (msg.display.sound != null) {
                PlayerEntity player = Minecraft.getInstance().player;
                if (player != null) {
                    Minecraft.getInstance().getSoundManager().play(new SimpleSound(msg.display.sound, SoundCategory.MASTER, 1, 1, player.getX(), player.getY(), player.getZ()));
                }
            }
            Minecraft.getInstance().setScreen(new DisplayQuestScreen(msg.display, msg.confirmationButtons));
        });
        context.get().setPacketHandled(true);
    }
}
