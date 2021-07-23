package com.feywild.feywild.network;

import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.setup.ClientProxy;
import com.feywild.feywild.util.ClientUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ItemEntityMessage {
    public ItemStack item;

    //Read msg from buf
    public ItemEntityMessage(PacketBuffer buf) {
        item = buf.readItem();
    }

    //constructor
    public ItemEntityMessage(ItemStack item) {
        this.item = item;
    }

    //Save msg to buf
    public void toBytes(PacketBuffer buf) {
        buf.writeItem(item);
    }

    //handle package data
    public void handle(Supplier<NetworkEvent.Context> ctx) {

        if(ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                ClientUtil.addBook(item);
            });
            ctx.get().setPacketHandled(true);
        }else{
            ctx.get().enqueueWork(()->{
                PlayerEntity entity = ctx.get().getSender();
                assert entity != null;
                entity.addItem(item);
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
