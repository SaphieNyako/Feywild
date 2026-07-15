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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

public record OpenQuestMessage(String questLineId, String backgroundName, boolean dismiss, int entityId) {

    public static void encode(OpenQuestMessage msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.questLineId());
        buf.writeUtf(msg.backgroundName());
        buf.writeBoolean(msg.dismiss());
        buf.writeInt(msg.entityId());
    }

    public static OpenQuestMessage decode(FriendlyByteBuf buf) {
        return new OpenQuestMessage(buf.readUtf(), buf.readUtf(), buf.readBoolean(), buf.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();

            if (serverPlayer == null) {
                return;
            }

            Level level = serverPlayer.level();

            Entity foundEntity = this.entityId() == -1
                    ? null
                    : level.getEntity(this.entityId());

            FeyBase questEntity;
            boolean temporarySprite = false;

            if (foundEntity instanceof FeyBase feyBase) {
                questEntity = feyBase;
            } else {
                questEntity = ModEntities.SPRITE.get().create(level);

                if (questEntity == null) {
                    Feywild.LOGGER.error(
                            "Could not create temporary Sprite for quest line '{}'",
                            this.questLineId()
                    );
                    return;
                }

                questEntity.moveTo(
                        serverPlayer.getX(),
                        serverPlayer.getEyeY(),
                        serverPlayer.getZ(),
                        serverPlayer.getYRot(),
                        serverPlayer.getXRot()
                );

                if (!level.addFreshEntity(questEntity)) {
                    Feywild.LOGGER.error(
                            "Could not add temporary Sprite for quest line '{}'",
                            this.questLineId()
                    );
                    return;
                }

                temporarySprite = true;
            }

            boolean shouldDismiss = temporarySprite || this.dismiss();

            Feywild.LOGGER.info(
                    "Opening quest '{}': entity={}, temporarySprite={}, dismiss={}",
                    this.questLineId(),
                    questEntity.getId(),
                    temporarySprite,
                    shouldDismiss
            );

            QuestGiverAPI.interactQuest(
                    serverPlayer,
                    questEntity.getId(),
                    Component.literal("Feywild Quest"),
                    InteractionHand.MAIN_HAND,
                    this.questLineId(),
                    this.backgroundName(),
                    shouldDismiss,
                    2
            );
        });

        context.setPacketHandled(true);
    }
}
