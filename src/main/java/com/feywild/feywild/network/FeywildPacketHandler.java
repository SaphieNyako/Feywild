package com.feywild.feywild.network;

import com.feywild.feywild.FeywildMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class FeywildPacketHandler {

    private static final String PROTOCOL_VERSION = "1.0";
    //Network channel
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(FeywildMod.MOD_ID, "packet"),
            () -> PROTOCOL_VERSION,
            s -> true,
            s -> true
    );
    private static int ID = 0;

    public static int nextPacketID() {
        return ID++;
    }

    //Register Packet
    public static void register() {
        INSTANCE.registerMessage(nextPacketID(), ItemMessage.class, ItemMessage::toBytes, ItemMessage::new, ItemMessage::handle);
        INSTANCE.registerMessage(nextPacketID(), ParticleMessage.class, ParticleMessage::toBytes, ParticleMessage::new, ParticleMessage::handle);
        INSTANCE.registerMessage(nextPacketID(), QuestMessage.class, QuestMessage::toBytes, QuestMessage::new, QuestMessage::handle);
        INSTANCE.registerMessage(nextPacketID(), DataMessage.class, DataMessage::toBytes, DataMessage::new, DataMessage::handle);
        INSTANCE.registerMessage(nextPacketID(), OpenQuestScreen.class, OpenQuestScreen::toBytes, OpenQuestScreen::new, OpenQuestScreen::handle);
    }

    public static void sendToPlayer(Object message, PlayerEntity entity) {
        if (entity instanceof ServerPlayerEntity)
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) entity), message);
    }

    // send a packet to all players near the pos withing a specific range
    public static void sendToPlayersInRange(World world, BlockPos pos, Object message, int range) {
        if (!world.isClientSide) {
            ((ServerWorld) world).getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false)
                    .filter(e -> e.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < range * range)
                    .forEach(e -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> e), message));
        }
    }

    //Ancient's note : I hate networking -_- switch to singleplayer :P
}
