package com.feywild.feywild.network.quest;

import com.feywild.feywild.quest.player.QuestData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ConfirmQuestHandler {

    public static void handle(ConfirmQuestSerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            if (player != null) {
                if (msg.accept) {
                    QuestData.get(player).acceptAlignment();
                } else {
                    QuestData.get(player).denyAlignment();
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
