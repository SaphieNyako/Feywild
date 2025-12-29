package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record PlaySoundMessage(SoundEvent sound, BlockPos pos) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PlaySoundMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "play_sound")
            );

    public static final StreamCodec<FriendlyByteBuf, PlaySoundMessage> STREAM_CODEC =
            StreamCodec.of(PlaySoundMessage::encode, PlaySoundMessage::decode);

    public static void encode(FriendlyByteBuf buf, PlaySoundMessage msg) {
        buf.writeResourceLocation(Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getKey(msg.sound())));
        buf.writeBlockPos(msg.pos());
    }

    public static PlaySoundMessage decode(FriendlyByteBuf buf) {
        ResourceLocation soundId = buf.readResourceLocation();
        SoundEvent sound = BuiltInRegistries.SOUND_EVENT.get(soundId);
        BlockPos pos = buf.readBlockPos();
        return new PlaySoundMessage(sound, pos);
    }

    public static void handle(PlaySoundMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = context.player().level();
            if (level != null) {
                level.playLocalSound(
                        msg.pos().getX() + 0.5, msg.pos().getY() + 0.5, msg.pos().getZ() + 0.5,
                        msg.sound(), SoundSource.NEUTRAL, 1.0f, 1.0f, false
                );
            }
        });
    }

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
