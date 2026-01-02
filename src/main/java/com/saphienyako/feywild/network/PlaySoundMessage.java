package com.saphienyako.feywild.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
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
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level == null) return;
            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(soundId);
            if (sound != null) {
                level.playLocalSound(
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        sound,
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                );
            }
        });
        context.setPacketHandled(true);
    }
}
