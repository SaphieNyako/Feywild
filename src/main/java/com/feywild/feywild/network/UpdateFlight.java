package com.feywild.feywild.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.function.Supplier;

public record UpdateFlight(Boolean canFly, Boolean flying) {


    public static class Serializer implements PacketSerializer<UpdateFlight> {


        @Override
        public Class<UpdateFlight> messageClass() {
            return UpdateFlight.class;
        }

        @Override
        public void encode(UpdateFlight msg, FriendlyByteBuf buffer) {
            buffer.writeBoolean(msg.canFly);
            buffer.writeBoolean(msg.flying);
        }

        @Override
        public UpdateFlight decode(FriendlyByteBuf buffer) {
            return new UpdateFlight(buffer.readBoolean(), buffer.readBoolean());
        }
    }

    public static class Handler implements PacketHandler<UpdateFlight> {

        @Override
        public Target target() {
            return Target.MAIN_THREAD;
        }

        @Override
        public boolean handle(UpdateFlight msg, Supplier<NetworkEvent.Context> ctx) {

            ctx.get().enqueueWork(() -> {
                Player player = Minecraft.getInstance().player;
                if (player != null) {
                    player.getAbilities().mayfly = msg.canFly;
                    player.getAbilities().flying = msg.flying;
                }
            });
            return true;
        }
    }

}
