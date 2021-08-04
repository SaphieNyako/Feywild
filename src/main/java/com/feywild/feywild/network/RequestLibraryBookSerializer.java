package com.feywild.feywild.network;

import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.PacketBuffer;

public class RequestLibraryBookSerializer implements PacketSerializer<RequestLibraryBookSerializer.Message> {

    @Override
    public Class<Message> messageClass() {
        return Message.class;
    }

    @Override
    public void encode(Message msg, PacketBuffer buffer) {
        buffer.writeVarInt(msg.idx);
    }

    @Override
    public Message decode(PacketBuffer buffer) {
        return new Message(buffer.readVarInt());
    }

    public static class Message {
        
        public final int idx;

        public Message(int idx) {
            this.idx = idx;
        }
    }
}
