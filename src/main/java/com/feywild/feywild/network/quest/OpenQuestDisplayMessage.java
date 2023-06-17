package com.feywild.feywild.network.quest;

import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.QuestDisplay;
import com.feywild.feywild.quest.player.ClientQuests;
import com.feywild.feywild.screens.DisplayQuestScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.function.Supplier;

import org.moddingx.libx.network.PacketHandler.Target;

public record OpenQuestDisplayMessage(QuestDisplay display, boolean confirmationButtons, int entityId, Alignment alignment) {

    public static class Serializer implements PacketSerializer<OpenQuestDisplayMessage> {

        @Override
        public Class<OpenQuestDisplayMessage> messageClass() {
            return OpenQuestDisplayMessage.class;
        }

        @Override
        public void encode(OpenQuestDisplayMessage msg, FriendlyByteBuf buffer) {
            msg.display().toNetwork(buffer);
            buffer.writeBoolean(msg.confirmationButtons());
            buffer.writeInt(msg.entityId());
            buffer.writeEnum(msg.alignment());
        }

        @Override
        public OpenQuestDisplayMessage decode(FriendlyByteBuf buffer) {
            QuestDisplay display = QuestDisplay.fromNetwork(buffer);
            boolean confirmationButtons = buffer.readBoolean();
            int id = buffer.readInt();
            Alignment alignment = buffer.readEnum(Alignment.class);
            return new OpenQuestDisplayMessage(display, confirmationButtons, id, alignment);
        }
    }

    public static class Handler implements PacketHandler<OpenQuestDisplayMessage> {

        @Override
        public Target target() {
            return Target.MAIN_THREAD;
        }

        @Override
        public boolean handle(OpenQuestDisplayMessage msg, Supplier<NetworkEvent.Context> ctx) {
            if (msg.display.sound != null) {
                Player player = Minecraft.getInstance().player;
                if (player != null && msg.display().sound != null) {
                    Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(msg.display().sound, SoundSource.MASTER, 1, 1, player.getRandom(), player.getX(), player.getY(), player.getZ()));
                }
            }
            if (msg.entityId() != -1) ClientQuests.lastTalkedEntityId = msg.entityId();
            Minecraft.getInstance().setScreen(new DisplayQuestScreen(msg.display(), msg.confirmationButtons(), ClientQuests.lastTalkedEntityId, msg.alignment()));
            return true;
        }
    }
}
