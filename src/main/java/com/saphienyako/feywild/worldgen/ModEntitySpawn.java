package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.ModEntities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.Arrays;

@Mod.EventBusSubscriber(modid = Feywild.MOD_ID)
public class ModEntitySpawn {

    @SubscribeEvent
    public static void onEntitySpawn(final BiomeLoadingEvent event) {
        addEntityToSpecificBiomes(event,
                ModEntities.SPRING_PIXIE.get(),
                3,
                1,
                1,
                Biomes.PLAINS.getRegistryName(),
                Biomes.SUNFLOWER_PLAINS.getRegistryName());

        addEntityToSpecificBiomes(event,
                ModEntities.AUTUMN_PIXIE.get(),
                3,
                1,
                1,
                Biomes.FOREST.getRegistryName(),
                Biomes.BIRCH_FOREST.getRegistryName(),
                Biomes.DARK_FOREST.getRegistryName(),
                Biomes.FLOWER_FOREST.getRegistryName(),
                Biomes.TAIGA.getRegistryName());

        addEntityToSpecificBiomes(event,
                ModEntities.SUMMER_PIXIE.get(),
                2,
                1,
                1,
                Biomes.DESERT.getRegistryName(),
                Biomes.SAVANNA.getRegistryName(),
                Biomes.SAVANNA_PLATEAU.getRegistryName(),
                Biomes.BADLANDS.getRegistryName());

        addEntityToSpecificBiomes(event,
                ModEntities.WINTER_PIXIE.get(),
                2,
                1,
                1,
                Biomes.ICE_SPIKES.getRegistryName(),
                Biomes.SNOWY_TUNDRA.getRegistryName(),
                Biomes.SNOWY_TAIGA.getRegistryName());
    }

    private static void addEntityToSpecificBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                                  int weight, int minCount, int maxCount, ResourceLocation... biomes) {
        ResourceLocation biomeName = event.getName();
        if (biomeName == null) return;

        boolean isBiomeSelected = Arrays.stream(biomes)
                .anyMatch(biome -> biome.equals(biomeName));

        if (isBiomeSelected) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                             int weight, int minCount, int maxCount) {
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
                .add(new MobSpawnInfo.Spawners(type, weight, minCount, maxCount));
    }
}
