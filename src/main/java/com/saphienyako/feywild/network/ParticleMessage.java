package com.saphienyako.feywild.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

public record ParticleMessage(Type type, BlockPos pos) {

    public static void encode(ParticleMessage msg, FriendlyByteBuf buffer) {
        buffer.writeEnum(msg.type());
        buffer.writeBlockPos(msg.pos);
    }

    public static ParticleMessage decode(FriendlyByteBuf buffer) {
        Type type = buffer.readEnum(Type.class);
        BlockPos pos = buffer.readBlockPos();
        return new ParticleMessage(type, pos);
    }

    //TODO Add Client Handler
    private static void withLevelDo(Consumer<Level> action) {
        var level = Minecraft.getInstance().level;
        if (level != null) action.accept(level);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        withLevelDo(level -> {
            final RandomSource ran = level.random;
            switch (this.type) {
                case DANDELION_FLUFF -> {
                    for (int i = 0; i < 40; i++) {
                        level.addParticle(ParticleTypes.END_ROD, true,this.pos.getX(), this.pos.getY(), this.pos().getZ(), 0.6 * (ran.nextDouble() - 0.5), 0.6 * (ran.nextDouble() - 0.3), 0.6 * (ran.nextDouble() - 0.5));
                    }
                }
                case FEY_HEART -> {
                    int y = this.pos.getY() + 1;
                    for (int i = 0; i < 5; i++) {
                        level.addParticle(ParticleTypes.HEART, true, this.pos.getX() - 0.3 + (0.6 * ran.nextDouble()), y + (1.9 * ran.nextDouble()), this.pos.getZ() - 0.3 + (0.6 * ran.nextDouble()), 0, 0, 0);
                    }
                }
                case CROPS_GROW -> {
                    for (int i = 0; i < 10; i++) {
                        level.addParticle(ParticleTypes.FLAME, true, this.pos.getX() - 0.3 + (0.6 * level.random.nextDouble()), this.pos.getY() + (0.8 * level.random.nextDouble()), this.pos.getZ() - 0.3 + (0.6 * level.random.nextDouble()), 0, 0, 0);
                    }
                }

                case MOB_COLLECT -> {
                    for (int i = 0; i < 20; i++) {
                        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, true, this.pos.getX() - 0.3 + (0.9 * level.random.nextDouble()), this.pos.getY() + (1.2 * level.random.nextDouble()), this.pos.getZ() - 0.3 + (0.9 * level.random.nextDouble()), 0, 0, 0);
                    }
                }

                case CROPS_RESET -> {
                    for (int i = 0; i < 10; i++) {
                        level.addParticle(ParticleTypes.GLOW, true, this.pos.getX() - 0.3 + (0.6 * level.random.nextDouble()), this.pos.getY() + (0.8 * level.random.nextDouble()), this.pos.getZ() - 0.3 + (0.6 * level.random.nextDouble()), 0, 0, 0);
                    }
                }




            }

        });
    }

    public enum Type {
        DANDELION_FLUFF, FEY_HEART, CROPS_GROW, CROPS_RESET, MOB_COLLECT
    }
}


