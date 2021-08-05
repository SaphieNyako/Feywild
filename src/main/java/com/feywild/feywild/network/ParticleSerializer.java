package com.feywild.feywild.network;


import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

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
    }

    @Override
    public Message decode(PacketBuffer buffer) {
        Type type = buffer.readEnum(Type.class);
        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();
        return new Message(type, x, y, z);
    }

    public static class Message {
        
        public final double x;
        public final double y;
        public final double z;
        public final Type type;
        
        public Message(Type type, double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.type = type;
        }
    }
    
    public enum Type {
        DANDELION_FLUFF, FEY_HEART
    }
}
