package com.saphienyako.feywild.worldgen;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.ModEntities;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = Feywild.MOD_ID)
public class ModEntitySpawn {

    @SubscribeEvent
    public static void onEntitySpawn(final BiomeLoadingEvent event) {
        addEntityToSpecificBiomes(event,
                ModEntities.SPRING_PIXIE.get(),
                3,
                1,
                1,
                Biomes.PLAINS,
                Biomes.SUNFLOWER_PLAINS);

        addEntityToSpecificBiomes(event,
                ModEntities.AUTUMN_PIXIE.get(),
                3,
                1,
                1,
                Biomes.FOREST,
                Biomes.BIRCH_FOREST,
                Biomes.DARK_FOREST,
                Biomes.FLOWER_FOREST,
                Biomes.TAIGA);

        addEntityToSpecificBiomes(event,
                ModEntities.SUMMER_PIXIE.get(),
                2,
                1,
                1,
                Biomes.DESERT,
                Biomes.SAVANNA,
                Biomes.SAVANNA_PLATEAU,
                Biomes.BADLANDS);

        addEntityToSpecificBiomes(event,
                ModEntities.WINTER_PIXIE.get(),
                2,
                1,
                1,
                Biomes.ICE_SPIKES,
                Biomes.SNOWY_PLAINS,
                Biomes.SNOWY_SLOPES,
                Biomes.SNOWY_TAIGA);
    }

    @SafeVarargs
    private static void addEntityToSpecificBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                                  int weight, int minCount, int maxCount, ResourceKey<Biome>... biomes) {
        // Goes through each entry in the biomes and sees if it matches the current biome we are loading
      //  boolean isBiomeSelected = Arrays.stream(biomes).map(ResourceKey::location).map(Object::toString).anyMatch(s -> s.equals(event.getName().toString()));
        boolean isBiomeSelected = Arrays.stream(biomes)
                .anyMatch(biome -> biome.location().equals(event.getName()));

        if(isBiomeSelected) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllBiomes(BiomeLoadingEvent event, EntityType<?> type,
                                             int weight, int minCount, int maxCount) {
        List<MobSpawnSettings.SpawnerData> base = event.getSpawns().getSpawner(type.getCategory());
        base.add(new MobSpawnSettings.SpawnerData(type,weight, minCount, maxCount));
    }


}
