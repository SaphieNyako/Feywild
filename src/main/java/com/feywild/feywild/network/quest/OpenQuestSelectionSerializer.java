package com.feywild.feywild.network.quest;

import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

import java.util.List;

public class OpenQuestSelectionSerializer implements PacketSerializer<OpenQuestSelectionSerializer.Message> {

    @Override
    public Class<Message> messageClass() {
        return Message.class;
    }

    @Override
    public void encode(Message msg, FriendlyByteBuf buffer) {
        buffer.writeComponent(msg.title);
        buffer.writeEnum(msg.alignment);
        buffer.writeVarInt(msg.quests.size());
        for (SelectableQuest quest : msg.quests) {
            quest.toNetwork(buffer);
        }
    }

    @Override
    public Message decode(FriendlyByteBuf buffer) {
        Component title = buffer.readComponent();
        Alignment alignment = buffer.readEnum(Alignment.class);
        int questSize = buffer.readVarInt();
        ImmutableList.Builder<SelectableQuest> quests = ImmutableList.builder();
        for (int i = 0; i < questSize; i++) {
            quests.add(SelectableQuest.fromNetwork(buffer));
        }
        return new Message(title, alignment, quests.build());
    }

    public static class Message {

        public final Component title;
        public final Alignment alignment;
        public final List<SelectableQuest> quests;

        public Message(Component title, Alignment alignment, List<SelectableQuest> quests) {
            this.title = title;
            this.alignment = alignment;
            this.quests = ImmutableList.copyOf(quests);
        }
    }
}
