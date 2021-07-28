package com.feywild.feywild.network;

import com.feywild.feywild.setup.ClientProxy;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class ParticleMessage {

    public double posX, posY, posZ, velX, velY, velZ;
    public int repeat, id;
    float speed;
    List<ParticleType> list = Arrays.asList(ParticleTypes.WITCH, ParticleTypes.HEART, ParticleTypes.END_ROD, ParticleTypes.HAPPY_VILLAGER, ParticleTypes.CRIT, ParticleTypes.FLAME, ParticleTypes.TOTEM_OF_UNDYING, ParticleTypes.ENCHANTED_HIT);

    //Read msg from buf
    public ParticleMessage(PacketBuffer buf) {
        velX = buf.readDouble();
        velY = buf.readDouble();
        velZ = buf.readDouble();
        repeat = buf.readInt();
        posX = buf.readDouble();
        posY = buf.readDouble();
        posZ = buf.readDouble();
        id = buf.readInt();
        speed = buf.readFloat();
    }

    //constructor
    public ParticleMessage(double posX, double posY, double posZ, double velX, double velY, double velZ, int repeat, int id, float speed) {
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.repeat = repeat;
        this.id = id;
        this.speed = speed;
    }

    //Save msg to buf
    public void toBytes(PacketBuffer buf) {
        buf.writeDouble(velX);
        buf.writeDouble(velY);
        buf.writeDouble(velZ);
        buf.writeInt(repeat);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeInt(id);
        buf.writeFloat(speed);
    }

    //handle package data
    public void handle(Supplier<NetworkEvent.Context> ctx) {

        World world = new ClientProxy().getClientWorld();
        Random random = new Random();
        ctx.get().enqueueWork(() -> {
            double newPosX, newPosY, newPosZ;
            int trueId = Math.abs(id);
            //summon particles based on info
            for (int i = 0; i < Math.abs(repeat); i++) {
                if (id < 0) {
                    world.addParticle((IParticleData) list.get(trueId), true, posX, posY, posZ, velX - random.nextDouble(), -random.nextDouble() + velY, -random.nextDouble() + velZ);
                } else if (repeat > 1) {
                    world.addParticle((IParticleData) list.get(id), true, posX - 0.3 + random.nextDouble(), posY - 0.3 + random.nextDouble(), posZ - 0.3 + random.nextDouble(), velX, velY, velZ);
                } else if (repeat < 0) {
                    newPosX = posX - 0.5 + random.nextDouble();
                    newPosY = posY - 0.5 + random.nextDouble();
                    newPosZ = posZ - 0.5 + random.nextDouble();
                    world.addParticle((IParticleData) list.get(id), true, newPosX, newPosY, newPosZ, (velX - newPosX) * speed, (velY - newPosY) * speed, (velZ - newPosZ) * speed);
                } else {
                    world.addParticle((IParticleData) list.get(id), true, posX, posY, posZ, velX, velY, velZ);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}