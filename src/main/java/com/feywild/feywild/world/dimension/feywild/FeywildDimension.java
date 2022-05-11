package com.feywild.feywild.world.dimension.feywild;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.dimension.feywild.setup.FeywildBiomeSource;
import com.feywild.feywild.world.dimension.feywild.setup.FeywildChunkGenerator;
import com.feywild.feywild.world.dimension.feywild.setup.FeywildSurfaceBuilder;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FeywildDimension {

    public static final ResourceKey<Level> FEYWILD_DIMENSION =
            ResourceKey.create(Registry.DIMENSION_REGISTRY, FeywildMod.getInstance().resource("feywild"));
    public static final BiomeDictionary.Type BIOME_TYPE = BiomeDictionary.Type.getType(FeywildMod.getInstance().modid);

    private static final Object LOCK = new Object();
    private static final Map<ResourceKey<Biome>, Climate.ParameterPoint> BIOMES = new HashMap<>();
    private static final Map<ResourceKey<Biome>, SurfaceRules.RuleSource> BIOME_SURFACE = new HashMap<>();
    private static final Set<StructureSet> STRUCTURES = new HashSet<>();
    private static final Set<Holder<StructureSet>> STRUCTURE_HOLDERS = new HashSet<>();

    public static void register() {
        System.out.println("Registering ModDimensions for " + FeywildMod.getInstance().modid);
        Registry.register(Registry.CHUNK_GENERATOR, FEYWILD_DIMENSION.location(), FeywildChunkGenerator.CODEC);
        Registry.register(Registry.BIOME_SOURCE, FEYWILD_DIMENSION.location(), FeywildBiomeSource.CODEC);
    }

    /* synchronized: allows only one thread to execute at any given time, preventing race conditions - https://www.baeldung.com/java-synchronized */

    public static void addBiome(Biome biome, BiomeConfiguration settings) {
        addBiome(biomeKey(biome), settings);
    }

    public static void addBiome(ResourceKey<Biome> biome, BiomeConfiguration settings) {
        synchronized (LOCK) {
            if (BIOMES.containsKey(biome))
                throw new IllegalStateException("Biome registered twice in feywild: " + biome);
            BIOMES.put(biome, settings.buildClimate());
            BIOME_SURFACE.put(biome, settings.buildSurface());
            BiomeDictionary.addTypes(biome, BIOME_TYPE);
        }
    }

    public static void addStructure(Holder<StructureSet> structure) {
        synchronized (LOCK) {
            if (STRUCTURES.contains(structure.value()))
                throw new IllegalStateException("Structure registered twice in feywild: " + structure);
            STRUCTURES.add(structure.value());
            STRUCTURE_HOLDERS.add(structure);
        }
    }

    public static List<Climate.ParameterPoint> buildAllClimateParameters() {
        synchronized (LOCK) {
            return BIOMES.values().stream().toList();
        }
    }

    public static Climate.ParameterList<Holder<Biome>> buildFeywildClimate(Function<ResourceKey<Biome>, Optional<Holder<Biome>>> biomeResolver) {
        synchronized (LOCK) {
            ImmutableList.Builder<Pair<Climate.ParameterPoint, Holder<Biome>>> list = ImmutableList.builder();
            for (Map.Entry<ResourceKey<Biome>, Climate.ParameterPoint> entry : biomes(BIOMES)) {
                ResourceKey<Biome> key = entry.getKey();
                list.add(new Pair<>(entry.getValue(), biomeResolver.apply(key).orElseThrow(
                        () -> new NoSuchElementException("Feywild biome not registered: " + key))));
            }
            return new Climate.ParameterList<>(list.build());
        }
    }

    public static SurfaceRules.RuleSource buildFeywildSurface() {
        synchronized (LOCK) {
            ImmutableList.Builder<SurfaceRules.RuleSource> sequence = ImmutableList.builder();
            for (Map.Entry<ResourceKey<Biome>, SurfaceRules.RuleSource> entry : biomes(BIOME_SURFACE)) {
                sequence.add(SurfaceRules.ifTrue(SurfaceRules.isBiome(entry.getKey()), entry.getValue()));
            }
            return FeywildSurfaceBuilder.buildSurface(SurfaceRules.sequence(sequence.build().toArray(new SurfaceRules.RuleSource[0])));  //??
        }
    }

    public static Predicate<Holder<StructureSet>> buildFeywildStructures(Registry<Biome> biomeRegistry) {
        synchronized (LOCK) {
            Set<ResourceKey<StructureSet>> keys = Set.copyOf(STRUCTURE_HOLDERS).stream().map(
                    holder -> holder.unwrapKey().orElseThrow(
                            () -> new IllegalStateException("Feywild structure with direct holder detected: " + holder + ", this is not allowed.")
                    )).collect(Collectors.toUnmodifiableSet());
            return holder -> keys.stream().anyMatch(holder::is);
        }
    }

    private static ResourceKey<Biome> biomeKey(Biome biome) {
        Optional<ResourceKey<Biome>> key = ForgeRegistries.BIOMES.getResourceKey(biome);
        //noinspection ConstantConditions
        if (key != null && key.isPresent()) {
            return key.get();
        } else {
            throw new IllegalStateException("Biome not registered: " + biome.getRegistryName() + ": " + biome);
        }
    }

    private static List<ResourceKey<Biome>> biomeKeys(List<Biome> biomes) {
        return biomes.stream().map(FeywildDimension::biomeKey).toList();
    }

    private static <T> List<Map.Entry<ResourceKey<Biome>, T>> biomes(Map<ResourceKey<Biome>, T> map) {
        synchronized (LOCK) {
            return map.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList();
        }
    }

    private record StructureSettings(int weight, StructurePlacement placement) {

    }
}
