package com.feywild.feywild.network.quest;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.quest.player.QuestData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.function.Supplier;

public record SelectQuestMessage(ResourceLocation quest, Component title, int id, Alignment alignment) {

    public static class Serializer implements PacketSerializer<SelectQuestMessage> {

        @Override
        public Class<SelectQuestMessage> messageClass() {
            return SelectQuestMessage.class;
        }

        @Override
        public void encode(SelectQuestMessage msg, FriendlyByteBuf buffer) {
            buffer.writeResourceLocation(msg.quest());
            buffer.writeComponent(msg.title);
            buffer.writeInt(msg.id);
            buffer.writeEnum(msg.alignment);
        }

        @Override
        public SelectQuestMessage decode(FriendlyByteBuf buffer) {
            ResourceLocation quest = buffer.readResourceLocation();
            Component title = buffer.readComponent();
            int id = buffer.readInt();
            Alignment alignment = buffer.readEnum(Alignment.class);
            return new SelectQuestMessage(quest, title, id, alignment);
        }
    }

    public static class Handler implements PacketHandler<SelectQuestMessage> {

        @Override
        public Target target() {
            return Target.MAIN_THREAD;
        }

        @Override
        public boolean handle(SelectQuestMessage msg, Supplier<NetworkEvent.Context> ctx) {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                QuestDisplay display = QuestData.get(player).getActiveQuestDisplay(msg.quest);
                if (display != null) {
                    FeywildMod.getNetwork().channel.reply(new OpenQuestDisplayMessage(display, false, msg.id, msg.alignment), ctx.get());
                }
            }
            return true;
        }
    }
}
