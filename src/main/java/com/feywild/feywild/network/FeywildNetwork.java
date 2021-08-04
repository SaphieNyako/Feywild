package com.feywild.feywild.network;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.network.NetworkX;
import net.minecraftforge.fml.network.NetworkDirection;

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
        // TODO currently missing:
        //  ParticleMessage (remove if possible)
        //  QuestMessage
        //  RequestOpenQuestScreen
    }

    @Override
    protected String getProtocolVersion() {
        return "2";
    }
}
