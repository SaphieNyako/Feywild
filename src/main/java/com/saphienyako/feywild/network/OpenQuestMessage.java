package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.quest_giver.QuestGiverAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record OpenQuestMessage(String questLineId, String backgroundName, boolean dismiss, int entityId) implements CustomPacketPayload {

    public static final Type<@NotNull OpenQuestMessage> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "open_quest"));

    public static final StreamCodec<FriendlyByteBuf, OpenQuestMessage> STREAM_CODEC =
            StreamCodec.of(OpenQuestMessage::encode, OpenQuestMessage::decode);

    private static void encode(FriendlyByteBuf buf, OpenQuestMessage msg) {
        buf.writeUtf(msg.questLineId());
        buf.writeUtf(msg.backgroundName());
        buf.writeBoolean(msg.dismiss());
        buf.writeInt(msg.entityId());
    }

    private static OpenQuestMessage decode(FriendlyByteBuf buf) {
        return new OpenQuestMessage(buf.readUtf(), buf.readUtf(), buf.readBoolean(), buf.readInt());
    }

    @SuppressWarnings("resource")
    public static void handle(OpenQuestMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer serverPlayer)) {
                return;
            }

            Level level = serverPlayer.level();

            Entity foundEntity = msg.entityId() == -1 ? null : level.getEntity(msg.entityId());

            FeyBase questEntity;
            boolean temporarySprite = false;

            if (foundEntity instanceof FeyBase feyBase) {
                questEntity = feyBase;
            } else {
                questEntity = ModEntities.SPRITE.get().create(serverPlayer.level(), EntitySpawnReason.SPAWN_ITEM_USE);

                if (questEntity == null) {
                    Feywild.LOGGER.error("Could not create temporary Sprite for quest line '{}'", msg.questLineId());
                    return;
                }

                questEntity.snapTo(serverPlayer.getX(), serverPlayer.getEyeY(), serverPlayer.getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());

                if (!level.addFreshEntity(questEntity)) {
                    Feywild.LOGGER.error("Could not add temporary Sprite for quest line '{}'", msg.questLineId());
                    return;
                }

                temporarySprite = true;
            }

            boolean shouldDismiss = temporarySprite || msg.dismiss();

            Feywild.LOGGER.info(
                    "Opening quest '{}': entity={}, temporarySprite={}, dismiss={}",
                    msg.questLineId(),
                    questEntity.getId(),
                    temporarySprite,
                    shouldDismiss
            );

            QuestGiverAPI.interactQuest(
                    serverPlayer,
                    questEntity.getId(),
                    Component.literal("Feywild Quest"),
                    InteractionHand.MAIN_HAND,
                    msg.questLineId(),
                    msg.backgroundName(),
                    shouldDismiss,
                    1.5
            );
        }).whenComplete((result, error) -> {
            if (error != null) {
                Feywild.LOGGER.error("Failed to open quest line '{}'", msg.questLineId(), error);
            }
        });
    }

    @Override
    public @NotNull Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}
