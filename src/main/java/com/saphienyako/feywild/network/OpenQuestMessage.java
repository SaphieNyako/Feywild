package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.SpriteEntity;

import com.saphienyako.quest_giver.QuestGiverAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

public record OpenQuestMessage(String questLineId, String backgroundName, boolean dismiss) {

    public static void encode(OpenQuestMessage msg,FriendlyByteBuf buf) {
        buf.writeUtf(msg.questLineId());
        buf.writeUtf(msg.backgroundName());
        buf.writeBoolean(msg.dismiss());
    }

    public static OpenQuestMessage decode(FriendlyByteBuf buf) {
        return new OpenQuestMessage(buf.readUtf(), buf.readUtf(), buf.readBoolean());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Player player = supplier.get().getSender();
            Level level = player.level;

            if (player instanceof ServerPlayer serverPlayer) {

                SpriteEntity entity = ModEntities.SPRITE.get().create(level);

                if (entity != null) {
                    entity.setPos(player.getEyePosition());
                    level.addFreshEntity(entity);
                    QuestGiverAPI.interactQuest(
                            serverPlayer,
                            entity.getId(),
                            Component.literal("Feywild Guide"),
                            InteractionHand.MAIN_HAND,
                            questLineId(),
                            backgroundName(),
                            dismiss(),
                            2
                    );
                }
            }
        });
    }
}
