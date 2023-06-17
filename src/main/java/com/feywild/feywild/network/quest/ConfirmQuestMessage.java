package com.feywild.feywild.network.quest;

import com.feywild.feywild.quest.player.QuestData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.function.Supplier;

import org.moddingx.libx.network.PacketHandler.Target;

public record ConfirmQuestMessage(boolean accept) {
    
    public static class Serializer implements PacketSerializer<ConfirmQuestMessage> {

        @Override
        public Class<ConfirmQuestMessage> messageClass() {
            return ConfirmQuestMessage.class;
        }

        @Override
        public void encode(ConfirmQuestMessage msg, FriendlyByteBuf buffer) {
            buffer.writeBoolean(msg.accept());
        }

        @Override
        public ConfirmQuestMessage decode(FriendlyByteBuf buffer) {
            return new ConfirmQuestMessage(buffer.readBoolean());
        }
    }
    
    public static class Handler implements PacketHandler<ConfirmQuestMessage> {

        @Override
        public Target target() {
            return Target.MAIN_THREAD;
        }

        @Override
        public boolean handle(ConfirmQuestMessage msg, Supplier<NetworkEvent.Context> ctx) {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                if (msg.accept) {
                    QuestData.get(player).acceptAlignment();
                } else {
                    QuestData.get(player).denyAlignment();
                }
            }
            return true;
        }
    }
}
