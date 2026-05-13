package com.saphienyako.feywild.network;

import com.saphienyako.feywild.network.handler.OpenLexiconMenuMessageClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record OpenLexiconMenuMessage(int entityId){

    public static void encode(OpenLexiconMenuMessage msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId());
    }
    public static OpenLexiconMenuMessage decode(FriendlyByteBuf buffer) {
        int id = buffer.readInt();
        return new OpenLexiconMenuMessage(id);
    }


    public void handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                OpenLexiconMenuMessageClientHandler.openMenu(entityId());
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
