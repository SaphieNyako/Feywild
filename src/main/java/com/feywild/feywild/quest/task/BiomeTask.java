package com.feywild.feywild.quest.task;

import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

public class BiomeTask implements TaskType<ResourceLocation, ResourceLocation> {

    public static final BiomeTask INSTANCE = new BiomeTask();

    private BiomeTask() {

    }

    @Override
    public Class<ResourceLocation> element() {
        return ResourceLocation.class;
    }

    @Override
    public Class<ResourceLocation> testType() {
        return ResourceLocation.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayerEntity player, ResourceLocation element, ResourceLocation match) {
        return element.equals(match);
    }

    @Override
    public ResourceLocation fromJson(JsonObject json) {
        return ResourceLocation.tryParse(json.get("biome_name").getAsString());
    }

    @Override
    public JsonObject toJson(ResourceLocation element) {
        JsonObject json = new JsonObject();
        json.addProperty("biome_name", element.toString());
        return json;
    }

}

