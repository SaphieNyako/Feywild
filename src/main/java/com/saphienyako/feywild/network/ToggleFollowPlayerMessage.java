package com.saphienyako.feywild.network;

import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.screen.FeyMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

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

        Level level = supplier.get().getSender().level();
        if (this.entityId() != -1) {

            FeyBase entity = (FeyBase) level.getEntity(this.entityId);
            if(entity != null) {
                entity.setFollowingPlayer(this.followingPlayer);
                if(!this.followingPlayer) {entity.setSummonPos(this.currentBlockPos);}
            }
        }
    }
}
