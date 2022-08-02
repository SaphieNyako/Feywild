package com.feywild.feywild.quest.util;

import com.feywild.feywild.quest.QuestDisplay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public record SelectableQuest(ResourceLocation id, Item icon, QuestDisplay display) {

    public void toNetwork(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(this.id());
        buffer.writeResourceLocation(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.icon()), "Item not registered"));
        this.display.toNetwork(buffer);
    }

    public static SelectableQuest fromNetwork(FriendlyByteBuf buffer) {
        ResourceLocation id = buffer.readResourceLocation();
        Item icon = ForgeRegistries.ITEMS.getValue(buffer.readResourceLocation());
        QuestDisplay display = QuestDisplay.fromNetwork(buffer);
        return new SelectableQuest(id, icon, display);
    }
}
