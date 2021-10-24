package com.feywild.feywild.data;

import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class DataUtils {
    
    public static JsonObject serializeWithNbt(ItemStack stack) {
        JsonObject itemJson = new JsonObject();
        itemJson.addProperty("item", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
        itemJson.addProperty("count", stack.getCount());
        if (stack.hasTag()) {
            itemJson.addProperty("nbt", stack.getOrCreateTag().toString());
        }
        return itemJson;
    }
}
