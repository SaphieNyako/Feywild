package com.feywild.feywild.events;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.SpringPixieEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FeywildMod.MOD_ID)
public class SpawnData {


    //Spawn entities on biome load
    @SubscribeEvent
    public static void spawnEntities(BiomeLoadingEvent event)
    {
        Biome.Climate biomeName = event.getClimate();
        addSpawn(event,biomeName, ModEntityTypes.SPRING_PIXIE.get(), EntityClassification.CREATURE,10, 1, 1);
        addSpawn(event,biomeName, ModEntityTypes.AUTUMN_PIXIE.get(), EntityClassification.CREATURE,10, 1, 1);
    }

    // check for climate and add
    private static void addSpawn(BiomeLoadingEvent event,Biome.Climate climate, EntityType<? extends Entity> type, EntityClassification classification,int weight, int min, int max)
    {
        if(type.equals(ModEntityTypes.SPRING_PIXIE.get()) && (climate.temperature >=0.6 || climate.temperature <=0.7f)) {
            MobSpawnInfo.Spawners spawnInfo = new MobSpawnInfo.Spawners(type, weight, min, max);
            event.getSpawns().getSpawner(classification).add(spawnInfo);
        }
        if(type.equals(ModEntityTypes.AUTUMN_PIXIE.get()) && (climate.temperature >=0.6 || climate.temperature <=0.7f)) {
            MobSpawnInfo.Spawners spawnInfo = new MobSpawnInfo.Spawners(type, weight, min, max);
            event.getSpawns().getSpawner(classification).add(spawnInfo);
        }

        if(type.equals(ModEntityTypes.SUMMER_PIXIE.get()) && (climate.temperature >=0.6 || climate.temperature <=0.7f)) {
            MobSpawnInfo.Spawners spawnInfo = new MobSpawnInfo.Spawners(type, weight, min, max);
            event.getSpawns().getSpawner(classification).add(spawnInfo);
        }
    }


    //Register spawning conditions
    public static void registerSpawn(){
        EntitySpawnPlacementRegistry.register(ModEntityTypes.SPRING_PIXIE.get(),
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntityTypes::spawnFey);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.AUTUMN_PIXIE.get(),
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntityTypes::spawnFey);

        EntitySpawnPlacementRegistry.register(ModEntityTypes.SUMMER_PIXIE.get(),
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntityTypes::spawnFey);
    }
}
