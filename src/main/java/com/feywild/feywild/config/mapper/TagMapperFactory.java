package com.feywild.feywild.config.mapper;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonPrimitive;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.moddingx.libx.annotation.config.RegisterMapper;
import org.moddingx.libx.config.gui.InputProperties;
import org.moddingx.libx.config.mapper.MapperFactory;
import org.moddingx.libx.config.mapper.ValueMapper;
import org.moddingx.libx.config.validator.ValidatorInfo;
import org.moddingx.libx.config.gui.ConfigEditor;

import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

@RegisterMapper
public class TagMapperFactory implements MapperFactory<TagKey<?>> {

    private final Map<Class<?>, TagMapper<?>> tagTypes = ImmutableMap.<Class<?>, TagMapper<?>>builder()
            .put(Biome.class, new TagMapper<>(Registry.BIOME_REGISTRY))
            .build();

    @Override
    public Class<TagKey<?>> type() {
        //noinspection unchecked
        return (Class<TagKey<?>>) (Class<?>) TagKey.class;
    }

    @Nullable
    @Override
    public ValueMapper<TagKey<?>, ?> create(Context ctx) {
        Class<?> cls = null;
        if (ctx.getGenericType() instanceof ParameterizedType pt && pt.getActualTypeArguments().length >= 1) {
            if (pt.getActualTypeArguments()[0] instanceof Class<?> arg) {
                cls = arg;
            } else if (pt.getActualTypeArguments()[0] instanceof ParameterizedType arg && arg.getRawType() instanceof Class<?> argCls) {
                cls = argCls;
            }
        }
        if (cls != null) {
            //noinspection unchecked
            return (ValueMapper<TagKey<?>, ?>) (ValueMapper<?, ?>) this.tagTypes.getOrDefault(cls, null);
        } else {
            return null;
        }
    }

    private static class TagMapper<T> implements ValueMapper<TagKey<T>, JsonPrimitive> {
        
        private final ResourceKey<? extends Registry<T>> registryKey;
        private final TagKeyInput input;

        private TagMapper(ResourceKey<? extends Registry<T>> registryKey) {
            this.registryKey = registryKey;
            this.input = new TagKeyInput();
        }

        @Override
        public Class<TagKey<T>> type() {
            //noinspection unchecked
            return (Class<TagKey<T>>) (Class<?>) TagKey.class;
        }

        @Override
        public Class<JsonPrimitive> element() {
            return JsonPrimitive.class;
        }

        @Override
        public TagKey<T> fromJson(JsonPrimitive json) {
            return TagKey.create(this.registryKey, new ResourceLocation(json.getAsString()));
        }

        @Override
        public JsonPrimitive toJson(TagKey<T> value) {
            return new JsonPrimitive(value.location().toString());
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ConfigEditor<TagKey<T>> createEditor(ValidatorInfo<?> validator) {
            return ConfigEditor.input(this.input, validator);
        }

        private class TagKeyInput implements InputProperties<TagKey<T>> {

            private final TagKey<T> defaultValue;

            private TagKeyInput() {
                this.defaultValue = TagKey.create(TagMapper.this.registryKey, new ResourceLocation("minecraft", "empty"));
            }

            @Override
            public TagKey<T> defaultValue() {
                return this.defaultValue;
            }

            @Override
            public TagKey<T> valueOf(String str) {
                return TagKey.create(TagMapper.this.registryKey, new ResourceLocation(str));
            }

            @Override
            public String toString(TagKey<T> key) {
                return key.location().toString();
            }

            @Override
            public boolean canInputChar(char chr) {
                return ResourceLocation.isAllowedInResourceLocation(chr);
            }

            @Override
            public boolean isValid(String str) {
                return ResourceLocation.tryParse(str) != null;
            }
        }
    }
}
