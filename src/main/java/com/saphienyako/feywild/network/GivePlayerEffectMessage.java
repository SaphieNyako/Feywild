package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record GivePlayerEffectMessage(ResourceLocation effectId, int duration, int amplifier,int entityId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<GivePlayerEffectMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "give_player_effect"));

    public static final StreamCodec<FriendlyByteBuf, GivePlayerEffectMessage> STREAM_CODEC =
            StreamCodec.of(GivePlayerEffectMessage::encode, GivePlayerEffectMessage::decode);


    private static void encode(FriendlyByteBuf buf, GivePlayerEffectMessage msg) {
       buf.writeResourceLocation(msg.effectId);
       buf.writeInt(msg.duration);
       buf.writeInt(msg.amplifier);
        buf.writeInt(msg.entityId);
    }

    private static GivePlayerEffectMessage decode(FriendlyByteBuf buf) {
        ResourceLocation resourceLocation = buf.readResourceLocation();
        int duration = buf.readInt();
        int amplifier = buf.readInt();
        int entityId = buf.readInt();
        return new GivePlayerEffectMessage(resourceLocation,duration,amplifier, entityId);
    }
    @SuppressWarnings("resource")
    public static void handle(GivePlayerEffectMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player == null || player.level().isClientSide) return;

            BuiltInRegistries.MOB_EFFECT
                    .getHolder(msg.effectId)
                    .ifPresent(holder -> {
                        player.addEffect(new MobEffectInstance(
                                holder,
                                msg.duration,
                                msg.amplifier
                        ));
                    });
            Level level = player.level();
            TreeEntBase entity = (TreeEntBase) context.player().level().getEntity(msg.entityId());
            if (entity != null) {
                if(FeywildConfig.voicesActive && entity.getVoiceActive()) {
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
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
