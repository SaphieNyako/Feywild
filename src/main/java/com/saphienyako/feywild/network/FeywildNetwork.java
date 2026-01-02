package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class FeywildNetwork {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Feywild.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ParticleMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ParticleMessage::decode)
                .encoder(ParticleMessage::encode)
                .consumer(ParticleMessage::handle)
                .add();

        net.messageBuilder(AltarParticleMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AltarParticleMessage::decode)
                .encoder(AltarParticleMessage::encode)
                .consumer(AltarParticleMessage::handle)
                .add();

        net.messageBuilder(PlaySoundMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlaySoundMessage::decode)
                .encoder(PlaySoundMessage::encode)
                .consumer(PlaySoundMessage::handle)
                .add();

        net.messageBuilder(OpenMenuMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(OpenMenuMessage::decode)
                .encoder(OpenMenuMessage::encode)
                .consumer(OpenMenuMessage::handle)
                .add();

        net.messageBuilder(ToggleFollowPlayerMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ToggleFollowPlayerMessage::decode)
                .encoder(ToggleFollowPlayerMessage::encode)
                .consumer(ToggleFollowPlayerMessage::handle)
                .add();

        net.messageBuilder(ToggleAbilityMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ToggleAbilityMessage::decode)
                .encoder(ToggleAbilityMessage::encode)
                .consumer(ToggleAbilityMessage::handle)
                .add();

        net.messageBuilder(ToggleVoiceMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ToggleVoiceMessage::decode)
                .encoder(ToggleVoiceMessage::encode)
                .consumer(ToggleVoiceMessage::handle)
                .add();

        net.messageBuilder(DismissEntityMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DismissEntityMessage::decode)
                .encoder(DismissEntityMessage::encode)
                .consumer(DismissEntityMessage::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static void sendParticles(Level level, ParticleMessage.Type type, BlockPos chunk) {
        if (level instanceof ServerLevel) {
           INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(chunk)), new ParticleMessage(type, chunk));
        }
    }

    public static void sendParticles(Level level, AltarParticleMessage.Type type, BlockPos chunk, int progress, int maxProgress) {
        if (level instanceof ServerLevel) {
            INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(chunk)), new AltarParticleMessage(type, chunk, progress, maxProgress));
        }
    }
}
