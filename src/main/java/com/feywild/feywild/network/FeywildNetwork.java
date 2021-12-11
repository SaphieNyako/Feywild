package com.feywild.feywild.network;

import com.feywild.feywild.network.quest.*;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.network.NetworkX;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public class FeywildNetwork extends NetworkX {

    public FeywildNetwork(ModX mod) {
        super(mod);
    }

    @Override
    protected Protocol getProtocol() {
        return Protocol.of("6");
    }

    @Override
    protected void registerPackets() {
        this.register(new OpenLibraryScreenSerializer(), () -> OpenLibraryScreenHandler::handle, NetworkDirection.PLAY_TO_CLIENT);
        this.register(new RequestItemSerializer(), () -> RequestItemHandler::handle, NetworkDirection.PLAY_TO_SERVER);
        this.register(new ParticleSerializer(), () -> ParticleHandler::handle, NetworkDirection.PLAY_TO_CLIENT);
        this.register(new TradesSerializer(), () -> TradesHandler::handle, NetworkDirection.PLAY_TO_CLIENT);
        this.register(new OpeningScreenSerializer(), () -> OpeningScreenHandler::handle, NetworkDirection.PLAY_TO_CLIENT);

        this.register(new OpenQuestSelectionSerializer(), () -> OpenQuestSelectionHandler::handle, NetworkDirection.PLAY_TO_CLIENT);
        this.register(new OpenQuestDisplaySerializer(), () -> OpenQuestDisplayHandler::handle, NetworkDirection.PLAY_TO_CLIENT);
        this.register(new SelectQuestSerializer(), () -> SelectQuestHandler::handle, NetworkDirection.PLAY_TO_SERVER);
        this.register(new ConfirmQuestSerializer(), () -> ConfirmQuestHandler::handle, NetworkDirection.PLAY_TO_SERVER);
    }

    public void sendParticles(Level level, ParticleSerializer.Type type, BlockPos pos) {
        this.sendParticles(level, type, pos, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
    }

    public void sendParticles(Level level, ParticleSerializer.Type type, double x, double y, double z) {
        this.sendParticles(level, type, x, y, z, 0, 0, 0);
    }

    public void sendParticles(Level level, ParticleSerializer.Type type, double x, double y, double z, double vx, double vy, double vz) {
        BlockPos chunk = new BlockPos((int) x, (int) y, (int) z);
        this.sendParticles(level, type, chunk, x, y, z, vx, vy, vz);
    }

    private void sendParticles(Level level, ParticleSerializer.Type type, BlockPos chunk, double x, double y, double z, double vx, double vy, double vz) {
        if (level instanceof ServerLevel) {
            this.channel.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(chunk)), new ParticleSerializer.Message(type, x, y, z, vx, vy, vz));
        }
    }
}
