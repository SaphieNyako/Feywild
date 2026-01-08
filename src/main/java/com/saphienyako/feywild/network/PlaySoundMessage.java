package com.saphienyako.feywild.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class PlaySoundMessage {

    private final ResourceLocation soundId;
    private final BlockPos pos;

    public PlaySoundMessage(ResourceLocation soundId, BlockPos pos) {
        this.soundId = soundId;
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    public ResourceLocation getSoundId() {
        return soundId;
    }

    public static void encode(PlaySoundMessage msg, PacketBuffer buffer) {
        buffer.writeResourceLocation(msg.soundId);
        buffer.writeBlockPos(msg.pos);

    }

    public static PlaySoundMessage decode(PacketBuffer buffer) {
        ResourceLocation soundId = buffer.readResourceLocation();
        BlockPos pos = buffer.readBlockPos();

        return new PlaySoundMessage(soundId, pos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            World level = Minecraft.getInstance().level;
            if (level == null) return;
            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(soundId);
            if (sound != null) {
                level.playLocalSound(
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        sound,
                        SoundCategory.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                );
            }
        });
        context.setPacketHandled(true);
    }
}
