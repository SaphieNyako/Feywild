package com.feywild.feywild.compat;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.quest.player.QuestData;
import io.github.noeppi_noeppi.mods.minemention.api.SpecialMention;
import io.github.noeppi_noeppi.mods.minemention.api.SpecialMentions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Predicate;

public class MineMentionCompat {
    
    public static void setup() {
        SpecialMentions.registerMention(FeywildMod.getInstance().resource("faction"), "faction", new FactionMention());
    }
    
    public static void availabilityChange(ServerPlayer player) {
        SpecialMentions.notifyAvailabilityChange(player);
    }
    
    public static class FactionMention implements SpecialMention {

        @Override
        public Component description() {
            return new TranslatableComponent("minemention.feywild.faction.description");
        }

        @Override
        public Predicate<ServerPlayer> selectPlayers(ServerPlayer sender) {
            return player -> {
                QuestData senderData = QuestData.get(sender);
                if (senderData.getAlignment() == null) return false;
                QuestData receiverData = QuestData.get(player);
                return senderData.getAlignment() == receiverData.getAlignment();
            };
        }

        @Override
        public boolean available(ServerPlayer sender) {
            QuestData data = QuestData.get(sender);
            return data.getAlignment() != null;
        }
    }
}
