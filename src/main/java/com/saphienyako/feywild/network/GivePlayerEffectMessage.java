package com.saphienyako.feywild.network;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record GivePlayerEffectMessage(ResourceLocation effectId, int duration, int amplifier,int entityId){


    public static void encode(GivePlayerEffectMessage msg, FriendlyByteBuf buf) {
       buf.writeResourceLocation(msg.effectId);
       buf.writeInt(msg.duration);
       buf.writeInt(msg.amplifier);
       buf.writeInt(msg.entityId);
    }

    public static GivePlayerEffectMessage decode(FriendlyByteBuf buf) {
        ResourceLocation resourceLocation = buf.readResourceLocation();
        int duration = buf.readInt();
        int amplifier = buf.readInt();
        int entityId = buf.readInt();
        return new GivePlayerEffectMessage(resourceLocation,duration,amplifier, entityId);
    }
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        Player player = supplier.get().getSender();
        if (player == null || player.level().isClientSide) return;

        ResourceKey<MobEffect> key = ResourceKey.create(Registries.MOB_EFFECT, this.effectId);

        BuiltInRegistries.MOB_EFFECT.getHolder(key).ifPresent(holder -> {
            player.addEffect(new MobEffectInstance(
                    holder.value(),
                    this.duration,
                    this.amplifier
            ));
        });

        Level level = player.level();
        TreeEntBase entity = (TreeEntBase) level.getEntity(this.entityId());
        if (entity != null) {
            if (ModConfig.COMMON.voice_active.get() && entity.getVoiceActive()) {
                level.playSound(
                        null,
                        entity.blockPosition(),
                        entity.getBlessingSound(),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                );
            }
        }
    }
}
