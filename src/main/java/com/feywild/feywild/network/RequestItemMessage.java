package com.feywild.feywild.network;

import com.feywild.feywild.FeyPlayerData;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.util.LibraryBooks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.function.Supplier;

public record RequestItemMessage(ScreenType type, int idx) {
    
    enum ScreenType {
        SCROLLS, BOOKS
    }
    
    public static class Serializer implements PacketSerializer<RequestItemMessage> {

        @Override
        public Class<RequestItemMessage> messageClass() {
            return RequestItemMessage.class;
        }

        @Override
        public void encode(RequestItemMessage msg, FriendlyByteBuf buffer) {
            buffer.writeEnum(msg.type());
            buffer.writeVarInt(msg.idx());
        }

        @Override
        public RequestItemMessage decode(FriendlyByteBuf buffer) {
            return new RequestItemMessage(buffer.readEnum(ScreenType.class), buffer.readVarInt());
        }
    }
    
    public static class Handler implements PacketHandler<RequestItemMessage> {

        @Override
        public Target target() {
            return Target.MAIN_THREAD;
        }

        @Override
        public boolean handle(RequestItemMessage msg, Supplier<NetworkEvent.Context> ctx) {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                switch (msg.type()) {
                    case SCROLLS -> {
                        if (!FeyPlayerData.get(player).getBoolean("feywild_got_scroll")) {
                            player.getInventory().add(getScroll(msg.idx()));
                            FeyPlayerData.get(player).putBoolean("feywild_got_scroll", true);
                        }
                    }
                    case BOOKS -> player.getInventory().add(LibraryBooks.getBook(msg.idx).copy());
                }
            }
            return true;
        }

        private static ItemStack getScroll(int id) {
            return switch (id) {
                case 0 -> new ItemStack(ModItems.summoningScrollSpringPixie);
                case 1 -> new ItemStack(ModItems.summoningScrollSummerPixie);
                case 2 -> new ItemStack(ModItems.summoningScrollAutumnPixie);
                case 3 -> new ItemStack(ModItems.summoningScrollWinterPixie);
                default -> ItemStack.EMPTY;
            };
        }
    }
}
