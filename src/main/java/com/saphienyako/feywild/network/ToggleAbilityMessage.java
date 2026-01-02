package com.saphienyako.feywild.network;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public record ToggleAbilityMessage (int entityId, boolean abilityActive) {

    public static void encode(ToggleAbilityMessage msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId());
        buffer.writeBoolean(msg.abilityActive());

    }

    public static ToggleAbilityMessage decode(FriendlyByteBuf buffer) {
        int id = buffer.readInt();
        boolean abilityActive = buffer.readBoolean();

        return new ToggleAbilityMessage(id, abilityActive);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        Player player = supplier.get().getSender();
        Level level = player.level;
        if (this.entityId() != -1) {
            FeyBase entity = (FeyBase) level.getEntity(this.entityId);
            if(entity != null) {
                entity.setAbilityActive(this.abilityActive);
                if(!this.abilityActive){
                    Objects.requireNonNull(player).sendMessage(entity.getFeyAbilityOffMessage(), player.getUUID());
                    if(ModConfig.CLIENT.voices_active.get() && entity.getVoiceActive()) {
                        FeywildNetwork.sendToPlayer(
                                new PlaySoundMessage(entity.getAbilityOffSound().getLocation(), entity.blockPosition()),
                                supplier.get().getSender());
                    }
                } else {
                    Objects.requireNonNull(player).sendMessage(entity.getFeyAbilityOnMessage(), player.getUUID());
                    if(ModConfig.CLIENT.voices_active.get() && entity.getVoiceActive()) {
                        FeywildNetwork.sendToPlayer(
                                new PlaySoundMessage(entity.getAbilityOnSound().getLocation(), entity.blockPosition()),
                                supplier.get().getSender());
                    }
                }
            }
        }
    }
}