package com.feywild.feywild.config.mapper;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.github.noeppi_noeppi.libx.config.ValueMapper;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

public class BiomeTypesMapper implements ValueMapper<List<BiomeDictionary.Type>, JsonArray> {

    @Override
    public Class<List<BiomeDictionary.Type>> type() {
        //noinspection unchecked
        return (Class<List<BiomeDictionary.Type>>) (Class<?>) List.class;
    }

    @Override
    public Class<JsonArray> element() {
        return JsonArray.class;
    }

    @Override
    public List<BiomeDictionary.Type> fromJSON(JsonArray json, Class<?> elementType) {
        ImmutableList.Builder<BiomeDictionary.Type> types = ImmutableList.builder();
        for (JsonElement elem : json) {
            types.add(BiomeDictionary.Type.getType(elem.getAsString()));
        }
        return types.build();
    }

    @Override
    public JsonArray toJSON(List<BiomeDictionary.Type> value, Class<?> elementType) {
        JsonArray json = new JsonArray();
        value.forEach(v -> json.add(v.getName()));
        return json;
    }
}
