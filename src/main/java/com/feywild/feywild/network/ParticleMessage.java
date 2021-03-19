package com.feywild.feywild.network;

import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.setup.ClientProxy;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;
import java.util.function.Supplier;

public class ParticleMessage {
    public double posX,posY,posZ,velX,velY,velZ;
    public int repeat;

    //Read msg from buf
    public ParticleMessage(PacketBuffer buf){
        velX = buf.readDouble();
        velY = buf.readDouble();
        velZ = buf.readDouble();
        repeat = buf.readInt();
        posX = buf.readDouble();
        posY = buf.readDouble();
        posZ = buf.readDouble();
    }

    //Save msg to buf
    public void toBytes(PacketBuffer buf){
        buf.writeDouble(velX);
        buf.writeDouble(velY);
        buf.writeDouble(velZ);
        buf.writeInt(repeat);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }

    //constructor
    public ParticleMessage(double posX, double posY, double posZ,double velX, double velY, double velZ, int repeat){
        this.velX = velX;
        this.velY = velY;
        this.velY = velZ;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.repeat = repeat;
    }

    //handle package data
    public void handle(Supplier<NetworkEvent.Context> ctx){

        World world = new ClientProxy().getClientWorld();

        ctx.get().enqueueWork(()-> {
            Random random = new Random();
            int revX, revZ;
            for(int i = 0; i < repeat; i++) {
                revX = random.nextBoolean()? -1 : 1;
                revZ = random.nextBoolean()? 1 : -1;
                world.addParticle(ParticleTypes.END_ROD, true, posX, posY, posZ,revX * velX,  velY,revZ * velZ);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
