package com.feywild.feywild.network;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.util.LibraryBooks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestItemHandler {
    
    public static void handle(RequestItemSerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity sender = context.get().getSender();
            ItemStack item = ItemStack.EMPTY;
            if(msg.state == RequestItemSerializer.State.books)
                item = LibraryBooks.getBook(msg.idx);
            else if (msg.state == RequestItemSerializer.State.scrolls)
                item = getScroll(msg.idx);


            if (!item.isEmpty() && sender != null) {
                sender.inventory.add(item);
            }
        });
        context.get().setPacketHandled(true);
    }

    private static ItemStack getScroll(int id){
        switch (id){
            case 0:
                return new ItemStack(ModItems.summoningScrollWinterPixie);
            case 1:
                return new ItemStack(ModItems.summoningScrollAutumnPixie);
            case 2:
                return new ItemStack(ModItems.summoningScrollSpringPixie);
            case 3:
                return new ItemStack(ModItems.summoningScrollSummerPixie);
            default:
                return ItemStack.EMPTY;
        }
    }
}
