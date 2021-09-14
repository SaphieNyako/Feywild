package com.feywild.feywild.network;

import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.PacketBuffer;

public class ParticleSerializer implements PacketSerializer<ParticleSerializer.Message> {

    @Override
    public Class<Message> messageClass() {
        return Message.class;
    }

    @Override
    public void encode(Message msg, PacketBuffer buffer) {
        buffer.writeEnum(msg.type);
        buffer.writeDouble(msg.x);
        buffer.writeDouble(msg.y);
        buffer.writeDouble(msg.z);
        buffer.writeDouble(msg.vx);
        buffer.writeDouble(msg.vy);
        buffer.writeDouble(msg.vz);
    }

    @Override
    public Message decode(PacketBuffer buffer) {
        Type type = buffer.readEnum(Type.class);
        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();
        double vx = buffer.readDouble();
        double vy = buffer.readDouble();
        double vz = buffer.readDouble();
        return new Message(type, x, y, z, vx, vy, vz);
    }

    public enum Type {
        DANDELION_FLUFF, FEY_HEART, WIND_WALK, ANIMAL_BREED, MONSTER_FIRE, CROPS_GROW
    }

    public static class Message {

        public final double x;
        public final double y;
        public final double z;
        public final double vx;
        public final double vy;
        public final double vz;
        public final Type type;

        public Message(Type type, double x, double y, double z) {
            this(type, x, y, z, 0, 0, 0);
        }

        public Message(Type type, double x, double y, double z, double vx, double vy, double vz) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
            this.type = type;
        }
    }
}
