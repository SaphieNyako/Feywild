package com.saphienyako.feywild.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.config.ConfigurableSpawnBiomeModifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomeModifierSerializers {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Feywild.MOD_ID);

    public static final RegistryObject<Codec<ConfigurableSpawnBiomeModifier>>
            FEYWILD_SPAWN_CODEC =
            BIOME_MODIFIER_SERIALIZERS.register(
                    "feywild_spawn",
                    () -> RecordCodecBuilder.create(instance ->
                            instance.group(
                                    Biome.LIST_CODEC
                                            .fieldOf("biomes")
                                            .forGetter(ConfigurableSpawnBiomeModifier::biomes),

                                    ForgeRegistries.ENTITY_TYPES
                                            .getCodec()
                                            .fieldOf("entity")
                                            .forGetter(ConfigurableSpawnBiomeModifier::entity)
                            ).apply(
                                    instance,
                                    ConfigurableSpawnBiomeModifier::new
                            )
                    )
            );

    public static void register(IEventBus eventBus) {
        BIOME_MODIFIER_SERIALIZERS.register(eventBus);
    }

}
