package com.saphienyako.feywild.network;

import com.saphienyako.feywild.entity.BeeMountEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record OpenBeeKnightMenuMessage (int entityId){

    public static void encode(OpenBeeKnightMenuMessage msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId());

    }

    public static OpenBeeKnightMenuMessage decode(FriendlyByteBuf buffer) {
        int id = buffer.readInt();


        return new OpenBeeKnightMenuMessage(id);
    }

    @SuppressWarnings("resource")
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            Level level = player.level;
            Entity entity = level.getEntity(this.entityId());

            if (entity instanceof BeeMountEntity bee_mount) {
                bee_mount.openCustomInventoryScreen(player);
            }
        });

        context.setPacketHandled(true);
    }


}
