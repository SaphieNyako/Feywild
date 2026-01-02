package com.saphienyako.feywild.network;

import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

public record AltarParticleMessage (AltarParticleMessage.Type type, BlockPos pos, int progress, int maxProgress) {

    public static void encode(AltarParticleMessage msg, FriendlyByteBuf buffer) {
        buffer.writeEnum(msg.type());
        buffer.writeBlockPos(msg.pos);
        buffer.writeInt(msg.progress);
        buffer.writeInt(msg.maxProgress);
    }

    public static AltarParticleMessage decode(FriendlyByteBuf buffer) {
        AltarParticleMessage.Type type = buffer.readEnum(AltarParticleMessage.Type.class);
        BlockPos pos = buffer.readBlockPos();
        int progress = buffer.readInt();
        int maxProgress = buffer.readInt();
        return new AltarParticleMessage(type, pos, progress, maxProgress);
    }

    private static void withLevelDo(Consumer<Level> action) {
        var level = Minecraft.getInstance().level;
        if (level != null) action.accept(level);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Level l = Minecraft.getInstance().level;
            if (l == null) return;

            switch (this.type) {
                case ALTAR_01 -> {
                    for (int i = 0; i < 20; i++) {
                        l.addParticle(ModParticles.FEY_SPARKLE_PARTICLE.get(), true,
                                this.pos.getX() + 0.5,
                                this.pos.getY() + 1.2,
                                this.pos.getZ() + 0.5,
                                0.5 - l.random.nextDouble(),
                                0.7 - l.random.nextDouble(),
                                0.5 - l.random.nextDouble());
                        l.addParticle(ParticleTypes.END_ROD, true,
                                this.pos.getX() + 0.5,
                                this.pos.getY() + 1.2,
                                this.pos.getZ() + 0.5,
                                0.5 - l.random.nextDouble(),
                                0.7 - l.random.nextDouble(),
                                0.5 - l.random.nextDouble());
                    }
                }
                case ALTAR_02 -> {
                    double progressScaled = this.progress / (double) maxProgress;
                    double anglePerStack = (2 * Math.PI) / 5;
                    for (int idx = 0; idx < 5; idx++) {
                        double shiftX = Math.cos((l.getGameTime() / 8.0) + (idx * anglePerStack)) * (1 - progressScaled);
                        double shiftZ = Math.sin((l.getGameTime() / 8.0) + (idx * anglePerStack)) * (1 - progressScaled);
                        l.addParticle(ModParticles.FEY_SPARKLE_PARTICLE.get(), true,
                                pos.getX() + 0.5 + shiftX,
                                pos.getY() + 1 + progressScaled,
                                pos.getZ() + 0.5 + shiftZ,
                                0, 0, 0);
                        l.addParticle(ParticleTypes.END_ROD, true,
                                pos.getX() + 0.5 + shiftX,
                                pos.getY() + 1 + progressScaled,
                                pos.getZ() + 0.5 + shiftZ,
                                0, 0, 0);
                    }
                }
                case ALTAR_03 -> {
                    if (l.random.nextFloat() < 0.1) {
                        l.addParticle(ModParticles.FEY_SPARKLE_PARTICLE.get(), true,
                                pos.getX() + Math.random(),
                                pos.getY() + 1 + Math.random(),
                                pos.getZ() + Math.random(),
                                0, 0, 0);
                    }
                    if (l.random.nextFloat() < 0.02) {
                        l.addParticle(ParticleTypes.END_ROD, true,
                                pos.getX() + Math.random(),
                                pos.getY() + 1 + Math.random(),
                                pos.getZ() + Math.random(),
                                0, 0, 0);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }

    public enum Type {
        ALTAR_01, ALTAR_02, ALTAR_03
    }
}
