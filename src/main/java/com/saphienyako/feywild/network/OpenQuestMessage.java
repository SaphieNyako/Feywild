package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.SpriteEntity;

import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.quest_giver.QuestGiverAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

public record OpenQuestMessage(String questLineId, String backgroundName, boolean dismiss, int entityId) {

    public static void encode(OpenQuestMessage msg,FriendlyByteBuf buf) {
        buf.writeUtf(msg.questLineId());
        buf.writeUtf(msg.backgroundName());
        buf.writeBoolean(msg.dismiss());
        buf.writeInt(msg.entityId());
    }

    public static OpenQuestMessage decode(FriendlyByteBuf buf) {
        return new OpenQuestMessage(buf.readUtf(), buf.readUtf(), buf.readBoolean(), buf.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Player player = supplier.get().getSender();
            Level level = player.level;

            if (player instanceof ServerPlayer serverPlayer) {

                FeyBase entity = (FeyBase) level.getEntity(this.entityId());
                if (!(entity instanceof FeyBase)) {
                    entity = ModEntities.SPRITE.get().create(level);
                    entity.setPos(player.getEyePosition());
                    level.addFreshEntity(entity);
                }

                if (entity != null) {
                    QuestGiverAPI.interactQuest(
                            serverPlayer,
                            entity.getId(),
                            Component.literal("Feywild Quest"),
                            InteractionHand.MAIN_HAND,
                            this.questLineId,
                            this.backgroundName,
                            this.dismiss,
                            2
                    );
                }
            }
        });
    }
}
