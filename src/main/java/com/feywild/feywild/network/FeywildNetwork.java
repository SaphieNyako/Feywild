package com.feywild.feywild.network;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.quest.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.network.NetworkX;

public class FeywildNetwork extends NetworkX {

    //Noeppi may know this better...
    private static final String PROTOCOL_VERSION = "7";
    public static final SimpleChannel NETWORK = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(FeywildMod.getInstance().modid, "network"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();
    public static int disc = 0;

    public FeywildNetwork(ModX mod) {
        super(mod);
    }

    @Override
    protected Protocol getProtocol() {
        return Protocol.of("7");
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

        NETWORK.registerMessage(disc++, SyncPlayerGuiStatus.class, SyncPlayerGuiStatus::encode, SyncPlayerGuiStatus::new, SyncPlayerGuiStatus::handle);
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

    public void sendTo(Object message, ServerPlayer player) {
        NETWORK.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }

    public void sendToServer(Object message) {
        NETWORK.sendToServer(message);
    }
}
