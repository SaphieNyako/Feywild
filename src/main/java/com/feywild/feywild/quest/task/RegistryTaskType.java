package com.feywild.feywild.quest.task;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class RegistryTaskType<T, X> implements TaskType<T, X> {

    private final String key;
    private final Class<T> cls;
    private final IForgeRegistry<T> registry;

    protected RegistryTaskType(String key, Class<T> cls, IForgeRegistry<T> registry) {
        this.key = key;
        this.cls = cls;
        this.registry = registry;
    }

    @Override
    public Class<T> element() {
        return this.cls;
    }

    @Override
    public T fromJson(JsonObject json) {
        ResourceLocation rl = ResourceLocation.tryParse(json.get(this.key).getAsString());
        T value = rl == null ? null : this.registry.getValue(rl);
        if (value == null) {
            throw new IllegalStateException("Can't load feywild quest task: " + this.element().getSimpleName() + " not found: " + rl);
        }
        return value;
    }

    @Override
    public JsonObject toJson(T element) {
        JsonObject json = new JsonObject();
        ResourceLocation rl = this.registry.getKey(element);
        if (rl == null) throw new IllegalStateException(this.element().getSimpleName() + " not registered: " + element);
        json.addProperty(this.key, rl.toString());
        return json;
    }
}
