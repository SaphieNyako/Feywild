package com.feywild.feywild.network;

import com.feywild.feywild.block.entity.LibraryBell;
import com.feywild.feywild.util.LibraryBooks;
import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.PacketBuffer;


public class OpeningScreenSerializer implements PacketSerializer<OpeningScreenSerializer.Message> {

    @Override
    public Class<Message> messageClass() {
        return Message.class;
    }

    @Override
    public void encode(Message msg, PacketBuffer buffer) {
        buffer.writeInt(msg.size);
    }

    @Override
    public Message decode(PacketBuffer buffer) {
       return new Message(buffer.readInt());
    }

    public static class Message {

        public int size;
        public Message(int size) {
            this.size = size;
        }
    }
}
