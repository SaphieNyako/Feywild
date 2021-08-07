package com.feywild.feywild.network;

import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class OpenLibraryScreenSerializer implements PacketSerializer<OpenLibraryScreenSerializer.Message> {

    @Override
    public Class<Message> messageClass() {
        return Message.class;
    }

    @Override
    public void encode(Message msg, PacketBuffer buffer) {
        buffer.writeComponent(msg.title);
        buffer.writeVarInt(msg.books.size());
        for (ItemStack book : msg.books) {
            buffer.writeItem(book);
        }
    }

    @Override
    public Message decode(PacketBuffer buffer) {
        ITextComponent title = buffer.readComponent();
        int bookSize = buffer.readVarInt();
        ImmutableList.Builder<ItemStack> books = ImmutableList.builder();
        for (int i = 0; i < bookSize; i++) {
            books.add(buffer.readItem());
        }
        return new Message(title, books.build());
    }

    public static class Message {
        
        public final ITextComponent title;
        public final List<ItemStack> books;

        public Message(ITextComponent title, List<ItemStack> books) {
            this.title = title;
            this.books = books;
        }
    }
}
