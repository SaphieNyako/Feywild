package com.saphienyako.feywild.network;

import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;


import java.util.function.Supplier;

public record OpenBellsnickelMenuMessage(int entityId) {

    public static void encode(OpenBellsnickelMenuMessage msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId());

    }

    public static OpenBellsnickelMenuMessage decode(FriendlyByteBuf buffer) {
        int id = buffer.readInt();


        return new OpenBellsnickelMenuMessage(id);
    }

    @SuppressWarnings("resource")
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            Level level = player.level();
            Entity entity = level.getEntity(this.entityId());

            if (entity instanceof BellsnickelEntity bellsnickel) {
                bellsnickel.openCustomInventoryScreen(player);
            }
        });

        context.setPacketHandled(true);
    }
}
