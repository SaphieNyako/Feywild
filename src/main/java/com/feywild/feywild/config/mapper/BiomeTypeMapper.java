package com.feywild.feywild.config.mapper;

import com.google.gson.JsonPrimitive;
import io.github.noeppi_noeppi.libx.annotation.config.RegisterMapper;
import io.github.noeppi_noeppi.libx.config.ValidatorInfo;
import io.github.noeppi_noeppi.libx.config.ValueMapper;
import io.github.noeppi_noeppi.libx.config.gui.ConfigEditor;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeTypeMapper implements ValueMapper<BiomeDictionary.Type, JsonPrimitive> {

    @Override
    public Class<BiomeDictionary.Type> type() {
        return BiomeDictionary.Type.class;
    }

    @Override
    public Class<JsonPrimitive> element() {
        return JsonPrimitive.class;
    }

    @Override
    public BiomeDictionary.Type fromJson(JsonPrimitive json) {
        return BiomeDictionary.Type.getType(json.getAsString());
    }

    @Override
    public JsonPrimitive toJson(BiomeDictionary.Type value) {
        return new JsonPrimitive(value.getName());
    }

    @Override
    public ConfigEditor<BiomeDictionary.Type> createEditor(ValidatorInfo<?> validator) {
        return ConfigEditor.toggle(BiomeDictionary.Type.getAll().stream().toList(), type -> new TextComponent(type.getName()));
    }
}
