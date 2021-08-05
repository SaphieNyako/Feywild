package com.feywild.feywild.network.quest;

import com.feywild.feywild.quest.QuestDisplay;
import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.PacketBuffer;

public class OpenQuestDisplaySerializer implements PacketSerializer<OpenQuestDisplaySerializer.Message> {

    @Override
    public Class<Message> messageClass() {
        return Message.class;
    }

    @Override
    public void encode(Message msg, PacketBuffer buffer) {
        msg.display.toNetwork(buffer);
        buffer.writeBoolean(msg.confirmationButtons);
    }

    @Override
    public Message decode(PacketBuffer buffer) {
        QuestDisplay display = QuestDisplay.fromNetwork(buffer);
        boolean confirmationButtons = buffer.readBoolean();
        return new Message(display, confirmationButtons);
    }

    public static class Message {

        public final QuestDisplay display;
        public final boolean confirmationButtons;
        
        public Message(QuestDisplay display, boolean confirmationButtons) {
            this.display = display;
            this.confirmationButtons = confirmationButtons;
        }
    }
}
