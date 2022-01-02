package com.feywild.feywild.data;

import com.google.gson.JsonObject;
import io.github.noeppi_noeppi.libx.annotation.meta.RemoveIn;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class DataUtils {
    
    // Replaced by method in LibX
    @Deprecated(forRemoval = true)
    @RemoveIn(minecraft = "1.18")
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
