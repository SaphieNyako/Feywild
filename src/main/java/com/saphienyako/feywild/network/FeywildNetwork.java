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
                .consumerMainThread(ParticleMessage::handle)
                .add();

        net.messageBuilder(AltarParticleMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AltarParticleMessage::decode)
                .encoder(AltarParticleMessage::encode)
                .consumerMainThread(AltarParticleMessage::handle)
                .add();

        net.messageBuilder(OpenMenuMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(OpenMenuMessage::decode)
                .encoder(OpenMenuMessage::encode)
                .consumerMainThread(OpenMenuMessage::handle)
                .add();


        net.messageBuilder(ToggleFollowPlayerMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ToggleFollowPlayerMessage::decode)
                .encoder(ToggleFollowPlayerMessage::encode)
                .consumerMainThread(ToggleFollowPlayerMessage::handle)
                .add();

        net.messageBuilder(ToggleAbilityMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ToggleAbilityMessage::decode)
                .encoder(ToggleAbilityMessage::encode)
                .consumerMainThread(ToggleAbilityMessage::handle)
                .add();

        net.messageBuilder(ToggleVoiceMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ToggleVoiceMessage::decode)
                .encoder(ToggleVoiceMessage::encode)
                .consumerMainThread(ToggleVoiceMessage::handle)
                .add();

        net.messageBuilder(DismissEntityMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DismissEntityMessage::decode)
                .encoder(DismissEntityMessage::encode)
                .consumerMainThread(DismissEntityMessage::handle)
                .add();

        net.messageBuilder(OpenBellsnickelMenuMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenBellsnickelMenuMessage::decode)
                .encoder(OpenBellsnickelMenuMessage::encode)
                .consumerMainThread(OpenBellsnickelMenuMessage::handle)
                .add();

        net.messageBuilder(OpenBeeKnightMenuMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenBeeKnightMenuMessage::decode)
                .encoder(OpenBeeKnightMenuMessage::encode)
                .consumerMainThread(OpenBeeKnightMenuMessage::handle)
                .add();

        net.messageBuilder(GivePlayerEffectMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GivePlayerEffectMessage::decode)
                .encoder(GivePlayerEffectMessage::encode)
                .consumerMainThread(GivePlayerEffectMessage::handle)
                .add();

        net.messageBuilder(MountTreeEntMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MountTreeEntMessage::decode)
                .encoder(MountTreeEntMessage::encode)
                .consumerMainThread(MountTreeEntMessage::handle)
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
