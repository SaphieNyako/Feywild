package com.feywild.feywild.network.quest;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.quest.player.QuestData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.function.Supplier;

public record SelectQuestMessage(ResourceLocation quest) {
    
    public static class Serializer implements PacketSerializer<SelectQuestMessage> {

        @Override
        public Class<SelectQuestMessage> messageClass() {
            return SelectQuestMessage.class;
        }

        @Override
        public void encode(SelectQuestMessage msg, FriendlyByteBuf buffer) {
            buffer.writeResourceLocation(msg.quest());
        }

        @Override
        public SelectQuestMessage decode(FriendlyByteBuf buffer) {
            return new SelectQuestMessage(buffer.readResourceLocation());
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
                    FeywildMod.getNetwork().channel.reply(new OpenQuestDisplayMessage(display, false), ctx.get());
                }
            }
            return true;
        }
    }
}
