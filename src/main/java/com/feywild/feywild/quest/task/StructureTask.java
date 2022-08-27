package com.feywild.feywild.quest.task;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public class StructureTask implements TaskType<ResourceLocation, ResourceLocation> {

    public static final StructureTask INSTANCE = new StructureTask();

    private StructureTask() {
        
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
    public boolean checkCompleted(ServerPlayer player, ResourceLocation element, ResourceLocation match) {
        return Objects.equals(element, match);
    }

    @Override
    public ResourceLocation fromJson(JsonObject json) {
        ResourceLocation rl = ResourceLocation.tryParse(json.get("structure").getAsString());
        if (rl == null) {
            throw new IllegalStateException("Can't load feywild quest task: invalid resource: " + json.get("structure"));
        }
        return rl;
    }

    @Override
    public JsonObject toJson(ResourceLocation element) {
        JsonObject json = new JsonObject();
        json.addProperty("structure", element.toString());
        return json;
    }
}
