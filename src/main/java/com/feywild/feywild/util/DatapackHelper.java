package com.feywild.feywild.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.ModList;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class DatapackHelper {

    @SuppressWarnings("TrivialFunctionalExpressionUsage")
    public static final Gson GSON = ((Supplier<Gson>) () -> {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        builder.setPrettyPrinting();
        return builder.create();
    }).get();
    
    public static <T> Map<ResourceLocation, T> loadData(ResourceManager rm, String path, BiFunction<ResourceLocation, JsonElement, T> factory) {
        try {
            ImmutableMap.Builder<ResourceLocation, T> map = ImmutableMap.builder();
            Collection<ResourceLocation> ids = rm.listResources(path, file -> file.endsWith(".json"));
            for (ResourceLocation id : ids) {
                String realPath = id.getPath();
                if (realPath.startsWith(path)) {
                    realPath = realPath.substring(path.length());
                    if (realPath.startsWith("/")) realPath = realPath.substring(1);
                }
                if (realPath.endsWith(".json")) {
                    realPath = realPath.substring(0, realPath.length() - 5);
                }
                ResourceLocation realId = new ResourceLocation(id.getNamespace(), realPath);
                Resource resource = rm.getResource(id);
                Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
                JsonElement json = GSON.fromJson(reader, JsonElement.class);
                map.put(realId, factory.apply(realId, json));
            }
            return map.build();
        } catch (IOException e) {
            throw new JsonSyntaxException(e);
        }
    }
    
    public static List<ItemStack> loadStackList(ResourceManager rm, String path, String name) {
        try {
            ImmutableList.Builder<ItemStack> list = ImmutableList.builder();
            for (String namespace : rm.getNamespaces()) {
                ResourceLocation id = new ResourceLocation(namespace, path + "/" + name + ".json");
                if (rm.hasResource(id)) {
                    Resource resource = rm.getResource(id);
                    Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
                    JsonElement json = GSON.fromJson(reader, JsonElement.class);
                    for (JsonElement elem : json.getAsJsonArray()) {
                        if (!elem.isJsonObject() || !elem.getAsJsonObject().has("mod") || ModList.get().isLoaded(elem.getAsJsonObject().get("mod").getAsString())) {
                            try {
                                list.add(CraftingHelper.getItemStack(elem.getAsJsonObject(), true));
                            } catch (JsonSyntaxException e) {
                                //
                            }
                        }
                    }
                }
            }
            return list.build();
        } catch (IOException e) {
            throw new JsonSyntaxException(e);
        }
    }
}
