package com.feywild.feywild.util;

import com.google.common.collect.Streams;
import com.google.gson.JsonElement;
import io.github.noeppi_noeppi.libx.datapack.DataLoader;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.ModList;

import java.io.IOException;
import java.util.List;

public class DatapackHelper {
    
    public static List<ItemStack> loadStackList(ResourceManager rm, String path, String name) {
        try {
            //noinspection UnstableApiUsage
            return DataLoader.joinJson(DataLoader.locate(rm, path + "/" + name + ".json", name), (id, data) -> data.getAsJsonArray())
                    .flatMap(Streams::stream).filter(JsonElement::isJsonObject).map(JsonElement::getAsJsonObject)
                    .filter(json -> !json.has("mod") || ModList.get().isLoaded(json.get("mod").getAsString()))
                    .map(json -> CraftingHelper.getItemStack(json, true)).filter(stack -> !stack.isEmpty()).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
