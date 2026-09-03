package com.saphienyako.feywild.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.config.ConfigurableSpawnBiomeModifier;
import com.saphienyako.feywild.entity.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomeModifiers {


    public static final ResourceKey<BiomeModifier> ADD_FEY_GEM_ORE = registerKey("add_fey_gem_ore");
    public static final ResourceKey<BiomeModifier> ADD_AUTUMN_TREE = registerKey("add_autumn_tree");
    public static final ResourceKey<BiomeModifier> ADD_SPRING_TREE = registerKey("add_spring_tree");
    public static final ResourceKey<BiomeModifier> ADD_SUMMER_TREE = registerKey("add_summer_tree");
    public static final ResourceKey<BiomeModifier> ADD_WINTER_TREE = registerKey("add_winter_tree");


    public static final ResourceKey<BiomeModifier> ADD_AUTUMN_PIXIE = registerKey("add_autumn_pixie");
    public static final ResourceKey<BiomeModifier> ADD_SPRING_PIXIE = registerKey("add_spring_pixie");
    public static final ResourceKey<BiomeModifier> ADD_SUMMER_PIXIE = registerKey("add_summer_pixie");
    public static final ResourceKey<BiomeModifier> ADD_WINTER_PIXIE = registerKey("add_winter_pixie");
    public static final ResourceKey<BiomeModifier> ADD_MANDRAGORA = registerKey("add_mandragora");
    public static final ResourceKey<BiomeModifier> ADD_BEE_KNIGHT = registerKey("add_bee_knight");
    public static final ResourceKey<BiomeModifier> ADD_BELLSNICKEL = registerKey("add_bellsnickel");
    public static final ResourceKey<BiomeModifier> ADD_SHROOMLING = registerKey("add_shroomling");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_FEY_GEM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.FEY_GEM_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_AUTUMN_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.FOREST),
                        biomes.getOrThrow(Biomes.DARK_FOREST)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.AUTUMN_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_SPRING_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.PLAINS),
                        biomes.getOrThrow(Biomes.MEADOW)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SPRING_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_SUMMER_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.SAVANNA),
                        biomes.getOrThrow(Biomes.SAVANNA_PLATEAU)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SUMMER_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_WINTER_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.SNOWY_PLAINS),
                        biomes.getOrThrow(Biomes.SNOWY_SLOPES),
                        biomes.getOrThrow(Biomes.GROVE)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WINTER_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        //MOB SPAWNS
        context.register(ADD_AUTUMN_PIXIE,
                new ConfigurableSpawnBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.FOREST),
                                biomes.getOrThrow(Biomes.BIRCH_FOREST),
                                biomes.getOrThrow(Biomes.DARK_FOREST),
                                biomes.getOrThrow(Biomes.OLD_GROWTH_BIRCH_FOREST),
                                biomes.getOrThrow(Biomes.OLD_GROWTH_PINE_TAIGA),
                                biomes.getOrThrow(Biomes.FLOWER_FOREST)
                        ),
                        ModEntities.AUTUMN_PIXIE.get()
                ));

        context.register(ADD_SPRING_PIXIE,
                new ConfigurableSpawnBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.PLAINS),
                                biomes.getOrThrow(Biomes.SUNFLOWER_PLAINS),
                                biomes.getOrThrow(Biomes.MEADOW)
                        ),
                        ModEntities.SPRING_PIXIE.get()
                ));

        context.register(ADD_SUMMER_PIXIE,
                new ConfigurableSpawnBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.DESERT),
                                biomes.getOrThrow(Biomes.SAVANNA),
                                biomes.getOrThrow(Biomes.SAVANNA_PLATEAU),
                                biomes.getOrThrow(Biomes.BADLANDS),
                                biomes.getOrThrow(Biomes.ERODED_BADLANDS)
                        ),
                        ModEntities.SUMMER_PIXIE.get()
                ));

        context.register(ADD_WINTER_PIXIE,
                new ConfigurableSpawnBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.ICE_SPIKES),
                                biomes.getOrThrow(Biomes.SNOWY_TAIGA),
                                biomes.getOrThrow(Biomes.SNOWY_SLOPES),
                                biomes.getOrThrow(Biomes.SNOWY_PLAINS)
                        ),
                        ModEntities.WINTER_PIXIE.get()
                ));

        context.register(ADD_MANDRAGORA,
                new ConfigurableSpawnBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.PLAINS),
                                biomes.getOrThrow(Biomes.SUNFLOWER_PLAINS),
                                biomes.getOrThrow(Biomes.MEADOW)
                        ),
                        ModEntities.MANDRAGORA.get()
                ));

        context.register(ADD_BEE_KNIGHT,
                new ConfigurableSpawnBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.MEADOW),
                                biomes.getOrThrow(Biomes.SUNFLOWER_PLAINS),
                                biomes.getOrThrow(Biomes.SAVANNA),
                                biomes.getOrThrow(Biomes.SAVANNA_PLATEAU),
                                biomes.getOrThrow(Biomes.DESERT),
                                biomes.getOrThrow(Biomes.ERODED_BADLANDS),
                                biomes.getOrThrow(Biomes.BIRCH_FOREST)
                        ),
                        ModEntities.BEE_MOUNT.get()
                ));

        context.register(ADD_SHROOMLING,
                new ConfigurableSpawnBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.MUSHROOM_FIELDS),
                                biomes.getOrThrow(Biomes.DARK_FOREST),
                                biomes.getOrThrow(Biomes.OLD_GROWTH_BIRCH_FOREST),
                                biomes.getOrThrow(Biomes.OLD_GROWTH_PINE_TAIGA)
                        ),
                        ModEntities.SHROOMLING.get()
                ));

        context.register(ADD_BELLSNICKEL,
                new ConfigurableSpawnBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.ICE_SPIKES),
                                biomes.getOrThrow(Biomes.SNOWY_TAIGA),
                                biomes.getOrThrow(Biomes.SNOWY_SLOPES),
                                biomes.getOrThrow(Biomes.SNOWY_PLAINS)
                        ),
                        ModEntities.BELLSNICKEL.get()
                ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Feywild.MOD_ID, name));
    }
}
