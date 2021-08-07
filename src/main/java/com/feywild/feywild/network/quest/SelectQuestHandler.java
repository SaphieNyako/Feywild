package com.feywild.feywild.network.quest;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.quest.player.QuestData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class SelectQuestHandler {

    public static void handle(SelectQuestSerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            if (player != null) {
                QuestDisplay display = QuestData.get(player).getActiveQuestDisplay(msg.quest);
                if (display != null) {
                    FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with(() -> player), new OpenQuestDisplaySerializer.Message(display, false)); 
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
