package com.saphienyako.feywild.network;

import com.saphienyako.feywild.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record PlaySoundMessage(ResourceLocation soundId, BlockPos pos) {

    public static void encode(PlaySoundMessage msg, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(msg.soundId());
        buffer.writeBlockPos(msg.pos());

    }

    public static PlaySoundMessage decode(FriendlyByteBuf buffer) {
        ResourceLocation soundId = buffer.readResourceLocation();
        BlockPos pos = buffer.readBlockPos();

        return new PlaySoundMessage(soundId, pos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        Level level = Minecraft.getInstance().level;
        SoundEvent sound = BuiltInRegistries.SOUND_EVENT.get(soundId);
        if (sound != null && ModConfig.CLIENT.voices_active.get()) {
            level.playLocalSound(
                    pos,
                    sound,
                    SoundSource.NEUTRAL,
                    1.0F,
                    1.0F,
                    false
            );
        }
    }
}
