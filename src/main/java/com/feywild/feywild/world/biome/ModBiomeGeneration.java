package com.feywild.feywild.world.biome;

import com.feywild.feywild.util.configs.Config;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class ModBiomeGeneration {
    
    public static void setupBiomes() {
        if (Config.MYTHIC.get() != 2) {
            addBiome(ModBiomes.blossomingWealds, BiomeManager.BiomeType.WARM, Config.SPRING_BIOME_CONFIG.getCachedWeight(), MAGICAL, FOREST);
            addBiome(ModBiomes.goldenSeelieFields, BiomeManager.BiomeType.WARM, Config.SUMMER_BIOME_CONFIG.getCachedWeight(), MAGICAL, HOT);
            addBiome(ModBiomes.eternalFall, BiomeManager.BiomeType.WARM, Config.AUTUMN_BIOME_CONFIG.getCachedWeight(), MAGICAL, MUSHROOM);
            addBiome(ModBiomes.frozenRetreat, BiomeManager.BiomeType.ICY, Config.WINTER_BIOME_CONFIG.getCachedWeight(), MAGICAL, COLD);
        }
    }

    private static void addBiome(Biome biome, BiomeManager.BiomeType type, int weight, BiomeDictionary.Type... types) {
        RegistryKey<Biome> key = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(biome)));
        BiomeDictionary.addTypes(key, BiomeDictionary.Type.OVERWORLD);
        BiomeDictionary.addTypes(key, types);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(key, weight));
    }
}
