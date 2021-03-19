package com.feywild.feywild.network;

import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.setup.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Supplier;

public class ItemMessage {
    public CompoundNBT tag;
    public BlockPos pos;

    //Read msg from buf
    public ItemMessage(PacketBuffer buf){
        tag = buf.readCompoundTag();
        pos = buf.readBlockPos();
    }

    //Save msg to buf
    public void toBytes(PacketBuffer buf){
        buf.writeCompoundTag(tag);
        buf.writeBlockPos(pos);
    }

    //constructor
    public ItemMessage(CompoundNBT tag, BlockPos pos){
        this.tag = tag;
        this.pos = pos;
    }

    //handle package data
    public void handle(Supplier<NetworkEvent.Context> ctx){

        World world = new ClientProxy().getClientWorld();

        ctx.get().enqueueWork(()-> {

            if(world.getTileEntity(pos) instanceof FeyAltarBlockEntity){
                LazyOptional<ItemStackHandler> handler = ((FeyAltarBlockEntity) world.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).cast();

                handler.ifPresent(itemStackHandler -> ((INBTSerializable<CompoundNBT>)itemStackHandler).deserializeNBT(tag));
            }

        });
        ctx.get().setPacketHandled(true);
    }
}
