package com.saphienyako.feywild.network;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ToggleAbilityMessage(int entityId, boolean abilityActive) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ToggleAbilityMessage> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "toggle_ability"));

    public static final StreamCodec<FriendlyByteBuf, ToggleAbilityMessage> STREAM_CODEC =
            StreamCodec.of(ToggleAbilityMessage::encode, ToggleAbilityMessage::decode);

    private static void encode(FriendlyByteBuf buf, ToggleAbilityMessage msg) {
        buf.writeInt(msg.entityId());
        buf.writeBoolean(msg.abilityActive());
    }

    private static ToggleAbilityMessage decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        boolean abilityActive = buf.readBoolean();
        return new ToggleAbilityMessage(id, abilityActive);
    }
    @SuppressWarnings("resource")
    public static void handle(ToggleAbilityMessage msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.level().isClientSide) return;

            Level level = player.level();
            if (msg.entityId() != -1) {
                FeyBase entity = (FeyBase) level.getEntity(msg.entityId());
                if (entity != null) {
                    entity.setAbilityActive(msg.abilityActive());
                    if (!msg.abilityActive()) {
                        player.sendSystemMessage(entity.getFeyAbilityOffMessage());
                        if(FeywildConfig.voicesActive && entity.getVoiceActive()) {
                            entity.playSound(entity.getAbilityOffSound());
                        }
                    } else {
                        player.sendSystemMessage(entity.getFeyAbilityOnMessage());
                        if(FeywildConfig.voicesActive && entity.getVoiceActive()) {
                            entity.playSound(entity.getAbilityOnSound());
                        }
                    }
                }
            }
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}