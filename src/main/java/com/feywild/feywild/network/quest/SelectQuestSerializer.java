package com.feywild.feywild.network.quest;

import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class SelectQuestSerializer implements PacketSerializer<SelectQuestSerializer.Message> {

    @Override
    public Class<Message> messageClass() {
        return Message.class;
    }

    @Override
    public void encode(Message msg, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(msg.quest);
    }

    @Override
    public Message decode(FriendlyByteBuf buffer) {
        return new Message(buffer.readResourceLocation());
    }

    public static class Message {
        
        public final ResourceLocation quest;
        
        public Message(ResourceLocation quest) {
            this.quest = quest;
        }
    }
}
