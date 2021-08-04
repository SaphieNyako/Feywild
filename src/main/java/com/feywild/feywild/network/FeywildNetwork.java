package com.feywild.feywild.network;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.network.NetworkX;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.PacketDistributor;

public class FeywildNetwork extends NetworkX {
    
    public FeywildNetwork(ModX mod) {
        super(mod);
    }
    
//    //Register Packet
//    public static void register() {
//        INSTANCE.registerMessage(nextPacketID(), ParticleMessage.class, ParticleMessage::toBytes, ParticleMessage::new, ParticleMessage::handle);
//        INSTANCE.registerMessage(nextPacketID(), QuestMessage.class, QuestMessage::toBytes, QuestMessage::new, QuestMessage::handle);
//        INSTANCE.registerMessage(nextPacketID(), OpenQuestScreen.class, OpenQuestScreen::toBytes, OpenQuestScreen::new, OpenQuestScreen::handle);
//        INSTANCE.registerMessage(nextPacketID(), RequestOpenQuestScreen.class, RequestOpenQuestScreen::toBytes,RequestOpenQuestScreen::new,RequestOpenQuestScreen::handle);
//        INSTANCE.registerMessage(nextPacketID(), ItemEntityMessage.class, ItemEntityMessage::toBytes,ItemEntityMessage::new,ItemEntityMessage::handle);
//        INSTANCE.registerMessage(nextPacketID(), LibrarianScreenMessage.class, LibrarianScreenMessage::toBytes,LibrarianScreenMessage::new,LibrarianScreenMessage::handle);
//    }
    
    @Override
    protected void registerPackets() {
        this.register(new OpenLibraryScreenSerializer(), () -> OpenLibraryScreenHandler::handle, NetworkDirection.PLAY_TO_CLIENT);
        this.register(new RequestLibraryBookSerializer(), () -> RequestLibraryBookHandler::handle, NetworkDirection.PLAY_TO_SERVER);
        this.register(new ParticleSerializer(), () -> ParticleHandler::handle, NetworkDirection.PLAY_TO_CLIENT);
        // TODO currently missing:
        //  QuestMessage
        //  RequestOpenQuestScreen
    }

    @Override
    protected String getProtocolVersion() {
        return "2";
    }
    
    public void sendParticles(World world, ParticleSerializer.Type type, BlockPos pos) {
        sendParticles(world, type, pos, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
    }

    public void sendParticles(World world, ParticleSerializer.Type type, double x, double y, double z) {
        BlockPos chunk = new BlockPos((int) x, (int) y, (int) z);
        sendParticles(world, type, chunk, x, y, z);
    }
    
    private void sendParticles(World world, ParticleSerializer.Type type, BlockPos chunk, double x, double y, double z) {
        if (world instanceof ServerWorld) {
            this.instance.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(chunk)), new ParticleSerializer.Message(type, x, y, z));
        }
    }
}