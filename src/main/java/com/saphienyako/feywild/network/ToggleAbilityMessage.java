package com.saphienyako.feywild.network;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ToggleAbilityMessage {

    private final int entityId;

    private final boolean abilityActive;

    public ToggleAbilityMessage(int entityId, boolean abilityActive) {
        this.entityId = entityId;
        this.abilityActive = abilityActive;
    }

    public boolean isAbilityActive() {
        return abilityActive;
    }

    public int getEntityId() {
        return entityId;
    }

    public static void encode(ToggleAbilityMessage msg, PacketBuffer buffer) {
        buffer.writeInt(msg.entityId);
        buffer.writeBoolean(msg.abilityActive);

    }

    public static ToggleAbilityMessage decode(PacketBuffer buffer) {
        int id = buffer.readInt();
        boolean abilityActive = buffer.readBoolean();

        return new ToggleAbilityMessage(id, abilityActive);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        PlayerEntity player = supplier.get().getSender();
        World level = player.level;
        if (this.entityId != -1) {
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