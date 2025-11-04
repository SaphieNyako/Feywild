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

    private static void withLevelDo(Consumer<Level> action) {
        var level = Minecraft.getInstance().level;
        if (level != null) action.accept(level);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        withLevelDo(l -> {
            final RandomSource ran = l.random;
            switch (this.type) {
                case DANDELION_FLUFF -> {
                    for (int i = 0; i < 40; i++) {
                        l.addParticle(ParticleTypes.END_ROD, true,this.pos.getX(), this.pos.getY(), this.pos().getZ(), 0.6 * (ran.nextDouble() - 0.5), 0.6 * (ran.nextDouble() - 0.3), 0.6 * (ran.nextDouble() - 0.5));
                    }
                }
                case FEY_HEART -> {
                    int y = this.pos.getY() + 1;
                    for (int i = 0; i < 5; i++) {
                        l.addParticle(ParticleTypes.HEART, true, this.pos.getX() - 0.3 + (0.6 * ran.nextDouble()), y + (0.6 * ran.nextDouble()), this.pos.getZ() - 0.3 + (0.6 * ran.nextDouble()), 0, 0, 0);
                    }
                }
            }

        });
    }

    public enum Type {
        DANDELION_FLUFF, FEY_HEART
    }
}


