package com.feywild.feywild.quest.task;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

public abstract class RegistryTaskType<T, X> implements TaskType<ResourceKey<T>, X> {

    @Nonnull
    public final IForgeRegistry<T> registry;
    private final String key;

    protected RegistryTaskType(String key, @Nonnull IForgeRegistry<T> registry) {
        this.key = key;
        this.registry = registry;
    }

    @Override
    public Class<ResourceKey<T>> element() {
        //noinspection unchecked
        return (Class<ResourceKey<T>>) (Class<?>) ResourceKey.class;
    }

    @Override
    public ResourceKey<T> fromJson(JsonObject json) {
        ResourceLocation rl = ResourceLocation.tryParse(json.get(this.key).getAsString());
        if (rl == null || this.registry.getValue(rl) == null) {
            throw new IllegalStateException("Can't load feywild quest task: " + this.registry.getRegistryName() + " not found: " + rl);
        }
        return ResourceKey.create(this.registry.getRegistryKey(), rl);
    }

    @Override
    public JsonObject toJson(ResourceKey<T> element) {
        JsonObject json = new JsonObject();
        json.addProperty(this.key, element.location().toString());
        return json;
    }
}
