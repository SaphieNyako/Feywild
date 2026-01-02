package com.saphienyako.feywild.network;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public record ToggleFollowPlayerMessage(int entityId, boolean followingPlayer, BlockPos currentBlockPos) {

    public static void encode(ToggleFollowPlayerMessage msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId());
        buffer.writeBoolean(msg.followingPlayer());
        buffer.writeBlockPos(msg.currentBlockPos());
    }

    public static ToggleFollowPlayerMessage decode(FriendlyByteBuf buffer) {
        int id = buffer.readInt();
        boolean followingPlayer = buffer.readBoolean();
        BlockPos currentBlockPos = buffer.readBlockPos();

        return new ToggleFollowPlayerMessage(id, followingPlayer,  currentBlockPos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
       Player player = supplier.get().getSender();
        Level level = player.level;
        if (this.entityId() != -1) {

            FeyBase entity = (FeyBase) level.getEntity(this.entityId);
            if(entity != null) {
                entity.setFollowingPlayer(this.followingPlayer);

                if(!this.followingPlayer) {
                    Objects.requireNonNull(player).sendMessage(entity.getFeyStayMessage(), player.getUUID());
                    if(ModConfig.CLIENT.voices_active.get() && entity.getVoiceActive()) {
                        FeywildNetwork.sendToPlayer(
                                new PlaySoundMessage(entity.getStaySound().getLocation(), entity.blockPosition()),
                                supplier.get().getSender());
                    }
                    entity.setSummonPos(this.currentBlockPos);
                } else {
                    Objects.requireNonNull(player).sendMessage(entity.getFeyFollowMessage(), player.getUUID());
                    if(ModConfig.CLIENT.voices_active.get() && entity.getVoiceActive()) {
                        FeywildNetwork.sendToPlayer(
                                new PlaySoundMessage(entity.getFollowSound().getLocation(), entity.blockPosition()),
                                supplier.get().getSender());
                    }
                }
            }
        }
    }
}
