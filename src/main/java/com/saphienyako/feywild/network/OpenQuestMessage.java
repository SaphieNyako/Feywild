package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.SpriteEntity;
import com.saphienyako.quest_giver.QuestGiverAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenQuestMessage(String questLineId, boolean dismiss) implements CustomPacketPayload {

    public static final Type<OpenQuestMessage> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "open_quest"));

    public static final StreamCodec<FriendlyByteBuf, OpenQuestMessage> STREAM_CODEC =
            StreamCodec.of(OpenQuestMessage::encode, OpenQuestMessage::decode);

    private static void encode(FriendlyByteBuf buf, OpenQuestMessage msg) {
        buf.writeUtf(msg.questLineId());
        buf.writeBoolean(msg.dismiss());
    }

    private static OpenQuestMessage decode(FriendlyByteBuf buf) {
        return new OpenQuestMessage(buf.readUtf(), buf.readBoolean());
    }

    public static void handle(OpenQuestMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Level level = context.player().level();

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
                            msg.questLineId(),
                            "spring_quest",
                            msg.dismiss
                    );
                }

                //TODO on questwindow close dismiss entity (quest_giver)
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
