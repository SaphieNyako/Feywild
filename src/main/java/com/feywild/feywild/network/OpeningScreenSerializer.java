package com.feywild.feywild.network;

import org.moddingx.libx.network.PacketSerializer;
import net.minecraft.network.FriendlyByteBuf;

public class OpeningScreenSerializer implements PacketSerializer<OpeningScreenSerializer.Message> {

    @Override
    public Class<Message> messageClass() {
        return Message.class;
    }

    @Override
    public void encode(Message msg, FriendlyByteBuf buffer) {
        //
    }

    @Override
    public Message decode(FriendlyByteBuf buffer) {
        return new Message();
    }

    public static class Message {

        public Message() {

        }
    }
}
