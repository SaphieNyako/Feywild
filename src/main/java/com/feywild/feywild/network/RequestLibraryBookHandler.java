package com.feywild.feywild.network;

import com.feywild.feywild.util.LibraryBooks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestLibraryBookHandler {
    
    public static void handle(RequestLibraryBookSerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity sender = context.get().getSender();
            ItemStack book = LibraryBooks.getBook(msg.idx);
            if (!book.isEmpty() && sender != null) {
                sender.inventory.add(book);
            }
        });
        context.get().setPacketHandled(true);
    }
}
