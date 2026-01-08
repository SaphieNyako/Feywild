package com.saphienyako.feywild.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.Random;
import java.util.function.Supplier;


public class ParticleMessage {

    private final Type type;
    private final BlockPos pos;

    public ParticleMessage(Type type, BlockPos pos) {
        this.type = type;
        this.pos = pos;
    }

    public Type getType() {
        return type;
    }

    public BlockPos getPos() {
        return pos;
    }

    public static void encode(ParticleMessage msg, PacketBuffer buffer) {
        buffer.writeInt(msg.getType().ordinal());
        buffer.writeBlockPos(msg.pos);
    }

    public static ParticleMessage decode(PacketBuffer buffer) {
        ParticleMessage.Type type = ParticleMessage.Type.values()[buffer.readInt()];
        BlockPos pos = buffer.readBlockPos();
        return new ParticleMessage(type, pos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level == null) return;

            switch (type) {
                case DANDELION_FLUFF: {
                    for (int i = 0; i < 40; i++) {
                        level.addParticle(ParticleTypes.END_ROD, true,
                                pos.getX(),
                                pos.getY(),
                                pos.getZ(),
                                0.6 * (level.random.nextDouble() - 0.5),
                                0.6 * (level.random.nextDouble() - 0.3),
                                0.6 * (level.random.nextDouble() - 0.5));
                    }
                    break;
                }
                case FEY_HEART: {
                    int y = pos.getY() + 1;
                    for (int i = 0; i < 5; i++) {
                        level.addParticle(ParticleTypes.HEART, true,
                                pos.getX() - 0.3 + (0.6 * level.random.nextDouble()),
                                y + (1.9 * level.random.nextDouble()),
                                pos.getZ() - 0.3 + (0.6 * level.random.nextDouble()),
                                0, 0, 0);
                    }
                    break;
                }
                case CROPS_GROW: {
                    for (int i = 0; i < 10; i++) {
                        level.addParticle(ParticleTypes.FLAME, true,
                                pos.getX() - 0.3 + (0.6 * level.random.nextDouble()),
                                pos.getY() + (0.8 * level.random.nextDouble()),
                                pos.getZ() - 0.3 + (0.6 * level.random.nextDouble()),
                                0, 0, 0);
                    }
                    break;
                }
                case MOB_COLLECT: {
                    for (int i = 0; i < 20; i++) {
                        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, true,
                                pos.getX() - 0.3 + (0.9 * level.random.nextDouble()),
                                pos.getY() + (1.2 * level.random.nextDouble()),
                                pos.getZ() - 0.3 + (0.9 * level.random.nextDouble()),
                                0, 0, 0);
                    }
                    break;
                }
                case CROPS_RESET: {
                    for (int i = 0; i < 10; i++) {
                        level.addParticle(ParticleTypes.CRIT, true,
                                pos.getX() - 0.3 + (0.6 * level.random.nextDouble()),
                                pos.getY() + (0.8 * level.random.nextDouble()),
                                pos.getZ() - 0.3 + (0.6 * level.random.nextDouble()),
                                0, 0, 0);
                    }
                    break;
                }
            }
        });

        context.setPacketHandled(true);
    }

    public enum Type {
        DANDELION_FLUFF, FEY_HEART, CROPS_GROW, CROPS_RESET, MOB_COLLECT
    }
}


