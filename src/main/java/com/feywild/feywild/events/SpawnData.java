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


    @SubscribeEvent
    public static void spawnEntities(BiomeLoadingEvent event)
    {
        Biome.Climate biomeName = event.getClimate();
        addSpawn(event,biomeName, ModEntityTypes.SPRING_PIXIE.get(), EntityClassification.CREATURE, 1, 1);
    }

    private static void addSpawn(BiomeLoadingEvent event,Biome.Climate cliamte, EntityType<? extends Entity> type, EntityClassification classification, int min, int max)
    {
        if(type.equals(ModEntityTypes.SPRING_PIXIE.get()) && (cliamte.temperature >=0.6 || cliamte.temperature <=0.7f)) {
            MobSpawnInfo.Spawners spawnInfo = new MobSpawnInfo.Spawners(type, 23, min, max);
            event.getSpawns().getSpawner(classification).add(spawnInfo);
        }
    }


    public static void registerSpawn(){
        EntitySpawnPlacementRegistry.register(ModEntityTypes.SPRING_PIXIE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntityTypes::spawnFey);
    }
}
