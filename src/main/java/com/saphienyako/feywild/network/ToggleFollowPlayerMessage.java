package com.saphienyako.feywild.network;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.entity.base.FeyBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ToggleFollowPlayerMessage {

    private final int entityId;
    private final boolean followingPlayer;
    private final BlockPos currentBlockPos;

    public ToggleFollowPlayerMessage(int entityId, boolean followingPlayer, BlockPos currentBlockPos) {
        this.entityId = entityId;
        this.followingPlayer = followingPlayer;
        this.currentBlockPos = currentBlockPos;
    }

    public int getEntityId() {
        return entityId;
    }

    public boolean isFollowingPlayer() {
        return followingPlayer;
    }

    public BlockPos getCurrentBlockPos() {
        return currentBlockPos;
    }

    public static void encode(ToggleFollowPlayerMessage msg, PacketBuffer buffer) {
        buffer.writeInt(msg.entityId);
        buffer.writeBoolean(msg.followingPlayer);
        buffer.writeBlockPos(msg.currentBlockPos);
    }

    public static ToggleFollowPlayerMessage decode(PacketBuffer buffer) {
        int id = buffer.readInt();
        boolean followingPlayer = buffer.readBoolean();
        BlockPos currentBlockPos = buffer.readBlockPos();

        return new ToggleFollowPlayerMessage(id, followingPlayer,  currentBlockPos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
       PlayerEntity player = supplier.get().getSender();
        World level = player.level;
        if (this.entityId != -1) {

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
