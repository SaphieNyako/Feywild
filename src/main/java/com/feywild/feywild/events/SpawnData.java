package com.feywild.feywild.events;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.entity.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

// TODO
@Mod.EventBusSubscriber(modid = "feywild")
public class SpawnData {

    public static String SpringBiome = "blossoming_wealds";
    public static String SummerBiome = "golden_seelie_fields";
    public static String AutumnBiome = "eternal_fall";
    public static String WinterBiome = "frozen_retreat";
    public static String Alfheim = "alfheim";

    @SubscribeEvent
    public static void spawnEntities(BiomeLoadingEvent event) {
        String biome = event.getName().toString();

        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<Type> types = BiomeDictionary.getTypes(key);

        if (!types.contains(Type.NETHER) && !types.contains(Type.END)
                && !types.contains(Type.OCEAN)) {

            addSpawn(event, ModEntityTypes.dwarfBlacksmith, EntityClassification.MONSTER, MobConfig.dwarf_blacksmith.weight, MobConfig.dwarf_blacksmith.min, MobConfig.dwarf_blacksmith.max);

            for (Type biomeName : MobConfig.spring_pixie.biomes) {
                if ((types.contains(biomeName) && !biome.contains(SummerBiome) && !biome.contains(AutumnBiome) && !biome.contains(WinterBiome)) || biome.contains(Alfheim)) {
                    addSpawn(event, ModEntityTypes.springPixie, EntityClassification.CREATURE,
                            MobConfig.spring_pixie.weight, MobConfig.spring_pixie.min, MobConfig.spring_pixie.max);
                }
            }

            for (Type biomeName : MobConfig.summer_pixie.biomes) {
                if ((types.contains(biomeName) && !biome.contains(SpringBiome) && !biome.contains(AutumnBiome) && !biome.contains(WinterBiome)) || biome.contains(Alfheim)) {
                    addSpawn(event, ModEntityTypes.summerPixie, EntityClassification.CREATURE,
                            MobConfig.summer_pixie.weight, MobConfig.summer_pixie.min, MobConfig.summer_pixie.max);
                }
            }

            for (Type biomeName : MobConfig.autumn_pixie.biomes) {
                if ((types.contains(biomeName) && !biome.contains(SpringBiome) && !biome.contains(SummerBiome) && !biome.contains(WinterBiome)) || biome.contains(Alfheim)) {
                    addSpawn(event, ModEntityTypes.autumnPixie, EntityClassification.CREATURE,
                            MobConfig.autumn_pixie.weight, MobConfig.autumn_pixie.min, MobConfig.autumn_pixie.max);
                }
            }

            for (Type biomeName : MobConfig.winter_pixie.biomes) {
                if ((types.contains(biomeName) && !biome.contains(SpringBiome) && !biome.contains(SummerBiome) && !biome.contains(AutumnBiome)) || biome.contains(Alfheim)) {
                    addSpawn(event, ModEntityTypes.winterPixie, EntityClassification.CREATURE,
                            MobConfig.winter_pixie.weight, MobConfig.winter_pixie.min, MobConfig.winter_pixie.max);
                }
            }
        }
    }

    private static void addSpawn(BiomeLoadingEvent event, EntityType<? extends Entity> type, EntityClassification classification, int weight, int min, int max) {
        MobSpawnInfo.Spawners spawnInfo = new MobSpawnInfo.Spawners(type, weight, min, max);
        event.getSpawns().getSpawner(classification).add(spawnInfo);
    }
}


