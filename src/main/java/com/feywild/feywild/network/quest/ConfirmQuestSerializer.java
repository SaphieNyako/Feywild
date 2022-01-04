package com.feywild.feywild.network.quest;

import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.FriendlyByteBuf;

public class ConfirmQuestSerializer implements PacketSerializer<ConfirmQuestSerializer.Message> {

    @Override
    public Class<Message> messageClass() {
        return Message.class;
    }

    @Override
    public void encode(Message msg, FriendlyByteBuf buffer) {
        buffer.writeBoolean(msg.accept);
    }

    @Override
    public Message decode(FriendlyByteBuf buffer) {
        return new Message(buffer.readBoolean());
    }

    public static class Message {

        public final boolean accept;

        public Message(boolean accept) {
            this.accept = accept;
        }
    }
}
