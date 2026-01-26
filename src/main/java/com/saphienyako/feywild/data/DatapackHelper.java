package com.saphienyako.feywild.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.ModList;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatapackHelper {

    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();


    public static List<ItemStack> loadStackList(ResourceManager manager, String folder, String filename) {
        List<ItemStack> result = new ArrayList<>();
        String targetPath = folder + "/" + filename + ".json";

        // List all resources under the folder
        Map<ResourceLocation, Resource> resources = manager.listResources(folder, loc -> loc.getPath().equals(targetPath));

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            ResourceLocation id = entry.getKey();
            Resource resource = entry.getValue();

            try (Reader reader = resource.openAsReader()) {
                JsonElement root = GsonHelper.fromJson(GSON, reader, JsonElement.class);

                if (!root.isJsonArray()) continue;

                for (JsonElement element : root.getAsJsonArray()) {
                    if (!element.isJsonObject()) continue;

                    JsonObject json = element.getAsJsonObject();

                    // Optional mod dependency check
                    if (json.has("mod")) {
                        String modid = json.get("mod").getAsString();
                        if (!ModList.get().isLoaded(modid)) continue;
                    }

                    // Convert JSON → ItemStack (without NBT)
                    ItemStack stack = itemStackFromJson(json);

                    if (!stack.isEmpty()) {
                        result.add(stack);
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException("Failed to load datapack resource: " + id, e);
            }
        }

        return result;
    }

    private static ItemStack itemStackFromJson(JsonObject json) {
        if (!json.has("item")) return ItemStack.EMPTY;

        try {
            // Split "modid:itemname" into namespace + path
            String itemString = json.get("item").getAsString();
            String[] parts = itemString.split(":", 2);
            ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);

            // Get item from registry
            Item item = BuiltInRegistries.ITEM.get(itemId);
            if (item == Items.AIR) return ItemStack.EMPTY;

            // Count defaults to 1
            int count = json.has("count") ? json.get("count").getAsInt() : 1;

            // Create plain ItemStack
            return new ItemStack(item, count);

        } catch (Exception e) {
            return ItemStack.EMPTY;
        }
    }
}
