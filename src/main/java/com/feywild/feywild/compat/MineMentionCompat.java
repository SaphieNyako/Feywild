package com.feywild.feywild.compat;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.quest.player.QuestData;
import io.github.noeppi_noeppi.mods.minemention.api.SpecialMention;
import io.github.noeppi_noeppi.mods.minemention.api.SpecialMentions;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Predicate;

public class MineMentionCompat {
    
    public static void setup() {
        SpecialMentions.registerMention(new ResourceLocation(FeywildMod.getInstance().modid, "faction"), "faction", new FactionMention());
    }
    
    public static void availabilityChange(ServerPlayerEntity player) {
        SpecialMentions.notifyAvailabilityChange(player);
    }
    
    public static class FactionMention implements SpecialMention {

        @Override
        public IFormattableTextComponent description() {
            return new TranslationTextComponent("minemention.feywild.faction.description");
        }

        @Override
        public Predicate<ServerPlayerEntity> selectPlayers(ServerPlayerEntity sender) {
            return player -> {
                QuestData senderData = QuestData.get(sender);
                if (senderData.getAlignment() == null) return false;
                QuestData receiverData = QuestData.get(player);
                return senderData.getAlignment() == receiverData.getAlignment();
            };
        }

        @Override
        public boolean available(ServerPlayerEntity sender) {
            QuestData data = QuestData.get(sender);
            return data.getAlignment() != null;
        }
    }
}
