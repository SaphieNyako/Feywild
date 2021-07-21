package com.feywild.feywild.util.serializer;

import com.feywild.feywild.util.ModUtil;
import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class UtilManager implements IFutureReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private static final String[] utilJson = new String[]{"feywild_util"};
    private static UtilManager instance;

    public static UtilManager instance() {
        if (instance == null) {
            instance = new UtilManager();
        }
        return instance;
    }

    @Nonnull
    @Override
    public CompletableFuture<Void> reload(IStage iStage, @Nonnull IResourceManager iResourceManager, @Nonnull IProfiler iProfiler, @Nonnull IProfiler iProfiler1, @Nonnull Executor executor, @Nonnull Executor executor1) {
        return CompletableFuture.allOf(CompletableFuture.runAsync(() ->
        {

            LibrarySerializer serializer = new LibrarySerializer();
            List<ItemStack> stacks = new LinkedList<>();
            for (String path : utilJson) {
                List<ResourceLocation> resources = (List<ResourceLocation>) iResourceManager.listResources(path, s -> s.endsWith(".json"));
                resources.forEach(resourceLocation -> {

                    try (InputStream stream = iResourceManager.getResource(resourceLocation).getInputStream(); Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {

                        if (resourceLocation.getPath().contains("books")) {
                            stacks.addAll(serializer.deserialize(Objects.requireNonNull(JSONUtils.fromJson(GSON, reader, JsonObject.class))));
                        }

                    } catch (IOException e) {
                        System.out.print("You are not abiding by the rules of the feywild! (Util setup is wrong)");
                        e.printStackTrace();
                    }
                });
            }
            ModUtil.setLibrarianBooks(stacks);
        }, executor)).thenCompose(iStage::wait);
    }

    public static class LibrarySerializer {

        public LibrarySerializer() {
        }

        public List<ItemStack> deserialize(JsonObject object) {
            if (object.get("type").getAsString().equals("library_books")) {
                List<ItemStack> ret = new LinkedList<>();
                JsonArray arr = object.getAsJsonArray("pool");
                for (JsonElement object1 : arr) {
                    JsonObject elementToObj = object1.getAsJsonObject();

                    try {
                        ret.add(ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(elementToObj, "book")));
                    } catch (JsonSyntaxException e) {
                        ret.add(ItemStack.EMPTY);
                    }
                }
                return ret;
            }
            return null;
        }
    }
}
