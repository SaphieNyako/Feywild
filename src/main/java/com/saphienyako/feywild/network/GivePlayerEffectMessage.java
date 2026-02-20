package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record GivePlayerEffectMessage(ResourceLocation effectId, int duration, int amplifier) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<GivePlayerEffectMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "give_player_effect"));

    public static final StreamCodec<FriendlyByteBuf, GivePlayerEffectMessage> STREAM_CODEC =
            StreamCodec.of(GivePlayerEffectMessage::encode, GivePlayerEffectMessage::decode);


    private static void encode(FriendlyByteBuf buf, GivePlayerEffectMessage msg) {
       buf.writeResourceLocation(msg.effectId);
       buf.writeInt(msg.duration);
       buf.writeInt(msg.amplifier);
    }

    private static GivePlayerEffectMessage decode(FriendlyByteBuf buf) {
        ResourceLocation resourceLocation = buf.readResourceLocation();
        int duration = buf.readInt();
        int amplifier = buf.readInt();

        return new GivePlayerEffectMessage(resourceLocation,duration,amplifier);
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
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
