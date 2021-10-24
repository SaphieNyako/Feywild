package com.feywild.feywild.network;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.util.LibraryBooks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestItemHandler {
    
    public static void handle(RequestItemSerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer sender = context.get().getSender();
            ItemStack item = ItemStack.EMPTY;
            if(msg.state == RequestItemSerializer.State.books)
                item = LibraryBooks.getBook(msg.idx);
            else if (msg.state == RequestItemSerializer.State.scrolls)
                item = getScroll(msg.idx);


            if (!item.isEmpty() && sender != null) {
                sender.getInventory().add(item);
            }
        });
        context.get().setPacketHandled(true);
    }

    private static ItemStack getScroll(int id){
        return switch (id) {
            case 0 -> new ItemStack(ModItems.summoningScrollWinterPixie);
            case 1 -> new ItemStack(ModItems.summoningScrollAutumnPixie);
            case 2 -> new ItemStack(ModItems.summoningScrollSpringPixie);
            case 3 -> new ItemStack(ModItems.summoningScrollSummerPixie);
            default -> ItemStack.EMPTY;
        };
    }
}
