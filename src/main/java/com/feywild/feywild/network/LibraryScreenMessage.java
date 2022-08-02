package com.feywild.feywild.network;

import com.feywild.feywild.screens.LibrarianScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public record LibraryScreenMessage(Component title, List<ItemStack> books) {
    
    public static class Serializer implements PacketSerializer<LibraryScreenMessage> {

        @Override
        public Class<LibraryScreenMessage> messageClass() {
            return LibraryScreenMessage.class;
        }

        @Override
        public void encode(LibraryScreenMessage msg, FriendlyByteBuf buffer) {
            buffer.writeComponent(msg.title());
            PacketUtil.writeList(msg.books(), buffer, FriendlyByteBuf::writeItem);
        }

        @Override
        public LibraryScreenMessage decode(FriendlyByteBuf buffer) {
            Component cmp = buffer.readComponent();
            List<ItemStack> stacks = PacketUtil.readList(buffer, FriendlyByteBuf::readItem);
            return new LibraryScreenMessage(cmp, stacks);
        }
    }

    public static class Handler implements PacketHandler<LibraryScreenMessage> {

        @Override
        public Target target() {
            return Target.MAIN_THREAD;
        }

        @Override
        public boolean handle(LibraryScreenMessage msg, Supplier<NetworkEvent.Context> ctx) {
            Minecraft.getInstance().setScreen(new LibrarianScreen(msg.title(), msg.books()));
            return true;
        }
    }
}
