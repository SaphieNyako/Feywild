package com.saphienyako.feywild.network;

import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AltarParticleMessage {

    private final  Type type;
    private final BlockPos pos;
    private final int progress;
    private final int maxProgress;

    public AltarParticleMessage(Type type, BlockPos pos, int progress, int maxProgress) {
        this.type = type;
        this.pos = pos;
        this.progress = progress;
        this.maxProgress = maxProgress;
    }

    public Type getType() {
        return type;
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }


    public static void encode(AltarParticleMessage msg, PacketBuffer buffer) {
        buffer.writeInt(msg.type.ordinal());
        buffer.writeBlockPos(msg.pos);
        buffer.writeInt(msg.progress);
        buffer.writeInt(msg.maxProgress);
    }

    public static AltarParticleMessage decode(PacketBuffer buffer) {
        AltarParticleMessage.Type type = AltarParticleMessage.Type.values()[buffer.readInt()];
        BlockPos pos = buffer.readBlockPos();
        int progress = buffer.readInt();
        int maxProgress = buffer.readInt();
        return new AltarParticleMessage(type, pos, progress, maxProgress);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level == null) return;

            switch (this.type) {
                case ALTAR_01: {
                    for (int i = 0; i < 20; i++) {
                        level.addParticle(ModParticles.FEY_SPARKLE_PARTICLE.get(), true,
                                this.pos.getX() + 0.5,
                                this.pos.getY() + 1.2,
                                this.pos.getZ() + 0.5,
                                0.5 - level.random.nextDouble(),
                                0.7 - level.random.nextDouble(),
                                0.5 - level.random.nextDouble());
                        level.addParticle(ParticleTypes.END_ROD, true,
                                this.pos.getX() + 0.5,
                                this.pos.getY() + 1.2,
                                this.pos.getZ() + 0.5,
                                0.5 - level.random.nextDouble(),
                                0.7 - level.random.nextDouble(),
                                0.5 - level.random.nextDouble());
                    }
                    break;
                }
                case ALTAR_02: {
                    double progressScaled = this.progress / (double) maxProgress;
                    double anglePerStack = (2 * Math.PI) / 5;
                    for (int idx = 0; idx < 5; idx++) {
                        double shiftX = Math.cos((level.getGameTime() / 8.0) + (idx * anglePerStack)) * (1 - progressScaled);
                        double shiftZ = Math.sin((level.getGameTime() / 8.0) + (idx * anglePerStack)) * (1 - progressScaled);
                        level.addParticle(ModParticles.FEY_SPARKLE_PARTICLE.get(), true,
                                pos.getX() + 0.5 + shiftX,
                                pos.getY() + 1 + progressScaled,
                                pos.getZ() + 0.5 + shiftZ,
                                0, 0, 0);
                        level.addParticle(ParticleTypes.END_ROD, true,
                                pos.getX() + 0.5 + shiftX,
                                pos.getY() + 1 + progressScaled,
                                pos.getZ() + 0.5 + shiftZ,
                                0, 0, 0);
                    }
                    break;
                }
                case ALTAR_03: {
                    if (level.random.nextFloat() < 0.1) {
                        level.addParticle(ModParticles.FEY_SPARKLE_PARTICLE.get(), true,
                                pos.getX() + Math.random(),
                                pos.getY() + 1 + Math.random(),
                                pos.getZ() + Math.random(),
                                0, 0, 0);
                    }
                    if (level.random.nextFloat() < 0.02) {
                        level.addParticle(ParticleTypes.END_ROD, true,
                                pos.getX() + Math.random(),
                                pos.getY() + 1 + Math.random(),
                                pos.getZ() + Math.random(),
                                0, 0, 0);
                    }
                    break;
                }
            }
        });
        context.setPacketHandled(true);
    }

    public enum Type {
        ALTAR_01, ALTAR_02, ALTAR_03
    }
}
