package com.feywild.feywild.network.quest;

import com.feywild.feywild.network.PacketUtil;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.feywild.feywild.screens.SelectQuestScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.List;
import java.util.function.Supplier;

public record OpenQuestSelectionMessage(Component title, Alignment alignment, List<SelectableQuest> quests) {

    public static class Serializer implements PacketSerializer<OpenQuestSelectionMessage> {

        @Override
        public Class<OpenQuestSelectionMessage> messageClass() {
            return OpenQuestSelectionMessage.class;
        }

        @Override
        public void encode(OpenQuestSelectionMessage msg, FriendlyByteBuf buffer) {
            buffer.writeComponent(msg.title());
            buffer.writeEnum(msg.alignment());
            PacketUtil.writeList(msg.quests(), buffer, (b, q) -> q.toNetwork(b));
        }

        @Override
        public OpenQuestSelectionMessage decode(FriendlyByteBuf buffer) {
            Component title = buffer.readComponent();
            Alignment alignment = buffer.readEnum(Alignment.class);
            List<SelectableQuest> quests = PacketUtil.readList(buffer, SelectableQuest::fromNetwork);
            return new OpenQuestSelectionMessage(title, alignment, quests);
        }
    }

    public static class Handler implements PacketHandler<OpenQuestSelectionMessage> {

        @Override
        public Target target() {
            return Target.MAIN_THREAD;
        }

        @Override
        public boolean handle(OpenQuestSelectionMessage msg, Supplier<NetworkEvent.Context> ctx) {
            Minecraft.getInstance().setScreen(new SelectQuestScreen(msg.title, msg.alignment, msg.quests));
            return true;
        }
    }
}
