package com.feywild.feywild.config.mapper;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.github.noeppi_noeppi.libx.config.ValueMapper;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.Set;

public class ResourceLocationMapper implements ValueMapper<Set<ResourceLocation>, JsonArray> {

    @Override
    public Class<Set<ResourceLocation>> type() {
        //noinspection unchecked
        return (Class<Set<ResourceLocation>>) (Class<?>) Set.class;
    }

    @Override
    public Class<JsonArray> element() {
        return JsonArray.class;
    }

    @Override
    public Set<ResourceLocation> fromJSON(JsonArray jsonElements, Class<?> aClass) {
        ImmutableSet.Builder<ResourceLocation> locations = ImmutableSet.builder();
        for (JsonElement elem : jsonElements) {
            locations.add(Objects.requireNonNull(ResourceLocation.tryParse(elem.getAsString())));
        }
        return locations.build();
    }

    @Override
    public JsonArray toJSON(Set<ResourceLocation> value, Class<?> aClass) {
        JsonArray json = new JsonArray();
        value.forEach(v -> json.add(v.toString()));
        return json;
    }
}