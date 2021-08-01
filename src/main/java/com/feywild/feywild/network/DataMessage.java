package com.feywild.feywild.network;

import com.feywild.feywild.setup.ClientProxy;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class DataMessage {

    public int data;
    BlockPos pos;

    //Read msg from buf
    public DataMessage(PacketBuffer buf) {
        data = buf.readInt();
        pos = buf.readBlockPos();
    }

    //constructor
    public DataMessage(int data, BlockPos pos) {
        this.data = data;
        this.pos = pos;
    }

    //Save msg to buf
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(data);
        buf.writeBlockPos(pos);
    }

    //handle package data
    public void handle(Supplier<NetworkEvent.Context> ctx) {

        World world = new ClientProxy().getClientWorld();

        ctx.get().enqueueWork(() -> {
            if (world.getBlockState(pos).getBlock() instanceof ClientDataBlock)
                ((ClientDataBlock) world.getBlockState(pos).getBlock()).setData(data);

        });
        ctx.get().setPacketHandled(true);
    }
}
