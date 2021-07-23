package com.feywild.feywild.network;

import com.feywild.feywild.util.ClientUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class LibrarianScreenMessage {

    //Read msg from buf
    public LibrarianScreenMessage(PacketBuffer buf) {

    }

    //constructor
    public LibrarianScreenMessage() {

    }

    //Save msg to buf
    public void toBytes(PacketBuffer buf) {
    }

    //handle package data
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if(ctx.get().getDirection().getReceptionSide().isClient()){
            ClientUtil.openLibrarianScreen();
        }
    }
}
