package com.feywild.feywild.network;

import com.feywild.feywild.network.quest.ConfirmQuestMessage;
import com.feywild.feywild.network.quest.OpenQuestDisplayMessage;
import com.feywild.feywild.network.quest.OpenQuestSelectionMessage;
import com.feywild.feywild.network.quest.SelectQuestMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.network.NetworkX;

public class FeywildNetwork extends NetworkX {

    public FeywildNetwork(ModX mod) {
        super(mod);
    }

    @Override
    protected Protocol getProtocol() {
        return Protocol.of("8");
    }

    @Override
    protected void registerPackets() {
        this.registerGame(NetworkDirection.PLAY_TO_CLIENT, new OpeningScreenMessage.Serializer(), () -> OpeningScreenMessage.Handler::new);
        this.registerGame(NetworkDirection.PLAY_TO_CLIENT, new LibraryScreenMessage.Serializer(), () -> LibraryScreenMessage.Handler::new);
        this.registerGame(NetworkDirection.PLAY_TO_SERVER, new RequestItemMessage.Serializer(), () -> RequestItemMessage.Handler::new);
        this.registerGame(NetworkDirection.PLAY_TO_CLIENT, new ParticleMessage.Serializer(), () -> ParticleMessage.Handler::new);
        this.registerGame(NetworkDirection.PLAY_TO_CLIENT, new TradesMessage.Serializer(), () -> TradesMessage.Handler::new);

        this.registerGame(NetworkDirection.PLAY_TO_CLIENT, new OpenQuestSelectionMessage.Serializer(), () -> OpenQuestSelectionMessage.Handler::new);
        this.registerGame(NetworkDirection.PLAY_TO_CLIENT, new OpenQuestDisplayMessage.Serializer(), () -> OpenQuestDisplayMessage.Handler::new);
        this.registerGame(NetworkDirection.PLAY_TO_SERVER, new SelectQuestMessage.Serializer(), () -> SelectQuestMessage.Handler::new);
        this.registerGame(NetworkDirection.PLAY_TO_SERVER, new ConfirmQuestMessage.Serializer(), () -> ConfirmQuestMessage.Handler::new);

        this.registerGame(NetworkDirection.PLAY_TO_CLIENT, new UpdateFlight.Serializer(), () -> UpdateFlight.Handler::new);
    }

    public void sendParticles(Level level, ParticleMessage.Type type, BlockPos pos) {
        this.sendParticles(level, type, pos, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0, 0);
    }

    public void sendParticles(Level level, ParticleMessage.Type type, double x, double y, double z) {
        this.sendParticles(level, type, x, y, z, 0, 0, 0);
    }

    public void sendParticles(Level level, ParticleMessage.Type type, double x, double y, double z, double vx, double vy, double vz) {
        BlockPos chunk = new BlockPos((int) x, (int) y, (int) z);
        this.sendParticles(level, type, chunk, x, y, z, vx, vy, vz);
    }

    private void sendParticles(Level level, ParticleMessage.Type type, BlockPos chunk, double x, double y, double z, double vx, double vy, double vz) {
        if (level instanceof ServerLevel) {
            this.channel.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(chunk)), new ParticleMessage(type, x, y, z, vx, vy, vz));
        }
    }
}
