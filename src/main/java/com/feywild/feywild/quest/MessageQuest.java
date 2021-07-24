package com.feywild.feywild.quest;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MessageQuest {

    private ResourceLocation id;

    private String string;
    private String name;
    private ItemStack icon;
    private boolean skip;

    public MessageQuest(ResourceLocation id, String string, String name, ItemStack icon, boolean skip) {
        this.id = id;
        this.skip = skip;
        this.string = string;
        this.name = name;
        this.icon = icon;
    }

    public ResourceLocation getId() {
        return id;
    }

    public String getText() {
        return string;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public boolean canSkip() {
        return skip;
    }
}
