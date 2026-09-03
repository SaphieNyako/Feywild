package com.saphienyako.feywild.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.config.ConfigurableSpawnBiomeModifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModBiomeModifierSerializers {

    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Feywild.MOD_ID);

    public static final Supplier<MapCodec<ConfigurableSpawnBiomeModifier>> FEYWILD_SPAWN = BIOME_MODIFIER_SERIALIZERS.register("feywild_spawn",
                    () -> RecordCodecBuilder.mapCodec(instance ->
                            instance.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(ConfigurableSpawnBiomeModifier::biomes),
                                    BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter(ConfigurableSpawnBiomeModifier::entity))
                                    .apply(instance, ConfigurableSpawnBiomeModifier::new)));


    public static void register(IEventBus eventBus) {
        BIOME_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
