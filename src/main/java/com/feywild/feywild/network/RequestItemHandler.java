package com.feywild.feywild.network;

import com.feywild.feywild.FeyPlayerData;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.util.LibraryBooks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestItemHandler {
    
    public static void handle(RequestItemSerializer.Message msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer sender = context.get().getSender();
            if (sender != null) {
                if (msg.state == RequestItemSerializer.State.books) {
                    sender.getInventory().add(LibraryBooks.getBook(msg.idx).copy());
                } else if (msg.state == RequestItemSerializer.State.scrolls) {
                    sender.getInventory().add(getScroll(msg.idx));
                    FeyPlayerData.get(sender).putBoolean("feywild_got_scroll", true);
                }
            }
        });
        context.get().setPacketHandled(true);
    }

    private static ItemStack getScroll(int id){
        return switch (id) {
            case 0 -> new ItemStack(ModItems.summoningScrollSpringPixie);
            case 1 -> new ItemStack(ModItems.summoningScrollSummerPixie);
            case 2 -> new ItemStack(ModItems.summoningScrollAutumnPixie);
            case 3 -> new ItemStack(ModItems.summoningScrollWinterPixie);
            default -> ItemStack.EMPTY;
        };
    }
}
