package com.feywild.feywild.events;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.util.configs.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = FeywildMod.MOD_ID)
public class SpawnData {

    public static List<BiomeDictionary.Type> biomeSpring = Config.SPRING_PIXIE_CONFIG.getBiomes();
    public static List<BiomeDictionary.Type> biomeSummer = Config.SUMMER_PIXIE_CONFIG.getBiomes();
    public static List<BiomeDictionary.Type> biomeAutumn = Config.AUTUMN_PIXIE_CONFIG.getBiomes();
    public static List<BiomeDictionary.Type> biomeWinter = Config.WINTER_PIXIE_CONFIG.getBiomes();

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

            addSpawn(event, ModEntityTypes.DWARF_BLACKSMITH.get(), EntityClassification.MONSTER, Config.DWARF_CONFIG.getWeight(), Config.DWARF_CONFIG.getMin(), Config.DWARF_CONFIG.getMax());

            for (Type biomeName : biomeSpring) {
                if (types.contains(biomeName) && !biome.contains(SummerBiome) && !biome.contains(AutumnBiome) && !biome.contains(WinterBiome) || biome.contains(Alfheim)) {
                    addSpawn(event, ModEntityTypes.SPRING_PIXIE.get(), EntityClassification.CREATURE,
                            Config.SPRING_PIXIE_CONFIG.getWeight(), Config.SPRING_PIXIE_CONFIG.getMin(), Config.AUTUMN_PIXIE_CONFIG.getMax());
                }
            }

            for (Type biomeName : biomeSummer) {
                if (types.contains(biomeName) && !biome.contains(SpringBiome) && !biome.contains(AutumnBiome) && !biome.contains(WinterBiome) || biome.contains(Alfheim)) {
                    addSpawn(event, ModEntityTypes.SUMMER_PIXIE.get(), EntityClassification.CREATURE,
                            Config.SUMMER_PIXIE_CONFIG.getWeight(), Config.SUMMER_PIXIE_CONFIG.getMin(), Config.SUMMER_PIXIE_CONFIG.getMax());
                }
            }

            for (Type biomeName : biomeAutumn) {
                if (types.contains(biomeName) && !biome.contains(SummerBiome) && !biome.contains(SpringBiome) && !biome.contains(WinterBiome) || biome.contains(Alfheim)) {
                    addSpawn(event, ModEntityTypes.AUTUMN_PIXIE.get(), EntityClassification.CREATURE,
                            Config.AUTUMN_PIXIE_CONFIG.getWeight(), Config.AUTUMN_PIXIE_CONFIG.getMin(), Config.AUTUMN_PIXIE_CONFIG.getMax());
                }
            }

            for (Type biomeName : biomeWinter) {
                if (types.contains(biomeName) && !biome.contains(SummerBiome) && !biome.contains(AutumnBiome) && !biome.contains(SpringBiome) || biome.contains(Alfheim)) {
                    addSpawn(event, ModEntityTypes.WINTER_PIXIE.get(), EntityClassification.CREATURE,
                            Config.WINTER_PIXIE_CONFIG.getWeight(), Config.WINTER_PIXIE_CONFIG.getMin(), Config.WINTER_PIXIE_CONFIG.getMax());
                }
            }

            addSpawn(event, ModEntityTypes.DWARF_BLACKSMITH.get(), EntityClassification.MONSTER, Config.DWARF_CONFIG.getWeight(), Config.DWARF_CONFIG.getMin(), Config.DWARF_CONFIG.getMax());

        }
    }

    private static void addSpawn(BiomeLoadingEvent event, EntityType<? extends Entity> type, EntityClassification classification, int weight, int min, int max) {
        MobSpawnInfo.Spawners spawnInfo = new MobSpawnInfo.Spawners(type, weight, min, max);
        event.getSpawns().getSpawner(classification).add(spawnInfo);
    }

    public static void registerSpawn() {
        EntitySpawnPlacementRegistry.register(ModEntityTypes.SPRING_PIXIE.get(),
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntityTypes::spawnFey);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.AUTUMN_PIXIE.get(),
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntityTypes::spawnFey);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.SUMMER_PIXIE.get(),
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntityTypes::spawnFey);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.WINTER_PIXIE.get(),
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntityTypes::spawnFey);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.DWARF_BLACKSMITH.get(),
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntityTypes::spawnDwarf);

    }

}


