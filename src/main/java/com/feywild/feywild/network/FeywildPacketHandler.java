package com.feywild.feywild.network;

import com.feywild.feywild.FeywildMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class FeywildPacketHandler {


    private static int ID = 0;
    public static int nextPacketID(){return ID++;}
    private static final String PROTOCOL_VERSION = "1.0";
    //Network channel
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(FeywildMod.MOD_ID, "packet"),
            () -> PROTOCOL_VERSION,
            s -> true,
            s -> true
    );


    //Register Packet
    public static void register(){
        INSTANCE.registerMessage(nextPacketID(), ItemMessage.class, ItemMessage::toBytes, ItemMessage::new, ItemMessage::handle);
        INSTANCE.registerMessage(nextPacketID(), ParticleMessage.class, ParticleMessage::toBytes, ParticleMessage::new, ParticleMessage::handle);
    }


    public static void sendToPlayersInRange(World world, BlockPos pos, Object message, int range){
        if (!world.isRemote) {
            ((ServerWorld) world).getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
                    .filter(e -> e.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < range * range)
                    .forEach(e -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> e), message));
        }
    }

    //Ancient's note : I hate networking -_- switch to singleplayer :P
}
