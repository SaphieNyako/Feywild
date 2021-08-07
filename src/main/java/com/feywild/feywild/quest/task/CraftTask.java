package com.feywild.feywild.quest.task;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;

public class CraftTask implements TaskType<Ingredient, ItemStack> {
    
    public static final CraftTask INSTANCE = new CraftTask();
    
    private CraftTask() {
        
    }

    @Override
    public Class<Ingredient> element() {
        return Ingredient.class;
    }

    @Override
    public Class<ItemStack> testType() {
        return ItemStack.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayerEntity player, Ingredient element, ItemStack match) {
        return element.test(match);
    }

    @Override
    public Ingredient fromJson(JsonObject json) {
        return Ingredient.fromJson(json.get("item"));
    }

    @Override
    public JsonObject toJson(Ingredient element) {
        JsonObject json = new JsonObject();
        json.add("item", element.toJson());
        return json;
    }

    @Nullable
    @Override
    public Item icon(Ingredient element) {
        ItemStack[] matching = element.getItems();
        if (matching.length == 1 && !matching[0].isEmpty()) {
            return matching[0].getItem();
        } else {
            return null;
        }
    }
}
