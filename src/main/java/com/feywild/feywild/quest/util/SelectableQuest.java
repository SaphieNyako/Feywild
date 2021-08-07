package com.feywild.feywild.quest.util;

import com.feywild.feywild.quest.QuestDisplay;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SelectableQuest {

    public final ResourceLocation id;
    public final Item icon;
    public final QuestDisplay display;

    public SelectableQuest(ResourceLocation id, Item icon, QuestDisplay display) {
        this.id = id;
        this.icon = icon;
        this.display = display;
    }
    
    public void toNetwork(PacketBuffer buffer) {
        buffer.writeResourceLocation(this.id);
        buffer.writeRegistryId(this.icon);
        this.display.toNetwork(buffer);
    }
    
    public static SelectableQuest fromNetwork(PacketBuffer buffer) {
        ResourceLocation id = buffer.readResourceLocation();
        Item icon = buffer.readRegistryId();
        QuestDisplay display = QuestDisplay.fromNetwork(buffer);
        return new SelectableQuest(id, icon, display);
    }
}
