package com.feywild.feywild.world.biome;

import com.feywild.feywild.config.CompatConfig;
import com.feywild.feywild.config.WorldGenConfig;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class ModBiomeGeneration {
    
    public static void setupBiomes() {
        if (CompatConfig.mythic_alfheim.overworld) {
            addBiome(ModBiomes.blossomingWealds, BiomeManager.BiomeType.WARM, WorldGenConfig.biomes.spring.weight, MAGICAL, FOREST);
            addBiome(ModBiomes.goldenSeelieFields, BiomeManager.BiomeType.WARM, WorldGenConfig.biomes.summer.weight, MAGICAL, HOT);
            addBiome(ModBiomes.eternalFall, BiomeManager.BiomeType.WARM, WorldGenConfig.biomes.autumn.weight, MAGICAL, MUSHROOM);
            addBiome(ModBiomes.frozenRetreat, BiomeManager.BiomeType.ICY, WorldGenConfig.biomes.winter.weight, MAGICAL, COLD);
        }
    }

    private static void addBiome(Biome biome, BiomeManager.BiomeType type, int weight, BiomeDictionary.Type... types) {
        RegistryKey<Biome> key = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(biome)));
        BiomeDictionary.addTypes(key, BiomeDictionary.Type.OVERWORLD);
        BiomeDictionary.addTypes(key, types);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(key, weight));
    }
}
