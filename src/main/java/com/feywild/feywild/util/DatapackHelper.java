package com.feywild.feywild.util;

import com.google.common.collect.Streams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.moddingx.libx.datapack.DataLoader;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.ModList;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public class DatapackHelper {

    @SuppressWarnings("TrivialFunctionalExpressionUsage")
    public static final Gson GSON = ((Supplier<Gson>) () -> {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        builder.setPrettyPrinting();
        return builder.create();
    }).get();

    public static List<ItemStack> loadStackList(ResourceManager rm, String path, String name) {
        try {
            return DataLoader.joinJson(DataLoader.locate(rm, path + "/" + name + ".json", name), (id, data) -> data.getAsJsonArray())
                    .flatMap(Streams::stream).filter(JsonElement::isJsonObject).map(JsonElement::getAsJsonObject)
                    .filter(json -> !json.has("mod") || ModList.get().isLoaded(json.get("mod").getAsString()))
                    .map(json -> CraftingHelper.getItemStack(json, true)).filter(stack -> !stack.isEmpty()).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
