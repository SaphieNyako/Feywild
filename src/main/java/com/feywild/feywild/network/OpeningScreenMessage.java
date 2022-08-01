package com.feywild.feywild.network;

import com.feywild.feywild.screens.OpeningScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.function.Supplier;

public record OpeningScreenMessage() {

    public static class Serializer implements PacketSerializer<OpeningScreenMessage> {

        @Override
        public Class<OpeningScreenMessage> messageClass() {
            return OpeningScreenMessage.class;
        }

        @Override
        public void encode(OpeningScreenMessage msg, FriendlyByteBuf buffer) {
            //
        }

        @Override
        public OpeningScreenMessage decode(FriendlyByteBuf buffer) {
            return new OpeningScreenMessage();
        }
    }

    public static class Handler implements PacketHandler<OpeningScreenMessage> {

        @Override
        public Target target() {
            return Target.MAIN_THREAD;
        }

        @Override
        public boolean handle(OpeningScreenMessage msg, Supplier<NetworkEvent.Context> ctx) {
            Minecraft.getInstance().setScreen(new OpeningScreen());
            return true;
        }
    }
}
