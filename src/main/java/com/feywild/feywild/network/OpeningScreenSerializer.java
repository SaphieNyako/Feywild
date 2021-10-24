package com.feywild.feywild.network;

import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.FriendlyByteBuf;


public class OpeningScreenSerializer implements PacketSerializer<OpeningScreenSerializer.Message> {

    @Override
    public Class<Message> messageClass() {
        return Message.class;
    }

    @Override
    public void encode(Message msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.size);
    }

    @Override
    public Message decode(FriendlyByteBuf buffer) {
       return new Message(buffer.readInt());
    }

    public static class Message {

        public int size;
        public Message(int size) {
            this.size = size;
        }
    }
}
