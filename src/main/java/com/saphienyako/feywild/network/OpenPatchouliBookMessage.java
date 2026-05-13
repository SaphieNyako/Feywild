package com.saphienyako.feywild.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.function.Supplier;

public record OpenPatchouliBookMessage(ResourceLocation bookId) {

    public static void encode(OpenPatchouliBookMessage msg, FriendlyByteBuf buffer){
        buffer.writeResourceLocation(msg.bookId());
    }

    public static OpenPatchouliBookMessage decode(FriendlyByteBuf buffer) {
        return new OpenPatchouliBookMessage(buffer.readResourceLocation());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Player player = supplier.get().getSender();

            if (player instanceof ServerPlayer serverPlayer) {
                PatchouliAPI.get().openBookGUI(serverPlayer, bookId());
            }
        });
    }
}
