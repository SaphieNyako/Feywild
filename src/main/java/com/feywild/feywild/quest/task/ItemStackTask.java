package com.feywild.feywild.quest.task;

import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.moddingx.libx.crafting.IngredientStack;

import javax.annotation.Nullable;

public class ItemStackTask implements TaskType<IngredientStack, ItemStack> {

    public static final ItemStackTask INSTANCE = new ItemStackTask();

    private ItemStackTask() {

    }

    @Override
    public Class<IngredientStack> element() {
        return IngredientStack.class;
    }

    @Override
    public Class<ItemStack> testType() {
        return ItemStack.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayer player, IngredientStack element, ItemStack match) {
        return element.test(match);
    }

    @Override
    public IngredientStack fromJson(JsonObject json) {
        Ingredient ingredient = Ingredient.fromJson(json.get("item"));
        int amount = json.has("amount") ? json.get("amount").getAsInt() : 1;
        return new IngredientStack(ingredient, amount);
    }

    @Override
    public JsonObject toJson(IngredientStack element) {
        JsonObject json = new JsonObject();
        json.add("item", element.ingredient().toJson());
        json.addProperty("amount", element.count());
        return json;
    }

    @Override
    public boolean repeatable() {
        return false;
    }

    @Nullable
    @Override
    public Item icon(IngredientStack element) {
        ItemStack[] matching = element.ingredient().getItems();
        if (matching.length == 1 && !matching[0].isEmpty()) {
            return matching[0].getItem();
        } else {
            return null;
        }
    }
}
