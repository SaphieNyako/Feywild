package com.saphienyako.feywild.config;

import com.mojang.serialization.Codec;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.worldgen.ModBiomeModifierSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;


public record ConfigurableSpawnBiomeModifier(HolderSet<Biome> biomes, EntityType<?> entity) implements BiomeModifier {


    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD || !biomes.contains(biome)) {
            return;
        }

        int weight = getSpawnWeight();

        if (weight <= 0) {
            return;
        }

        MobSpawnSettings.SpawnerData spawnData = new MobSpawnSettings.SpawnerData(entity, weight, 1, 1);
        builder.getMobSpawnSettings().addSpawn(entity.getCategory(), spawnData);
    }

    private int getSpawnWeight() {

        if (entity == ModEntities.AUTUMN_PIXIE.get()) {
            return ModConfig.COMMON.autumnPixieSpawnWeight.get();
        }

        if (entity == ModEntities.SPRING_PIXIE.get()) {
            return ModConfig.COMMON.springPixieSpawnWeight.get();
        }

        if (entity == ModEntities.SUMMER_PIXIE.get()) {
            return ModConfig.COMMON.summerPixieSpawnWeight.get();
        }

        if (entity == ModEntities.WINTER_PIXIE.get()) {
            return ModConfig.COMMON.winterPixieSpawnWeight.get();
        }

        if (entity == ModEntities.MANDRAGORA.get()) {
            return ModConfig.COMMON.mandragoraSpawnWeight.get();
        }

        if (entity == ModEntities.BEE_MOUNT.get()) {
            return ModConfig.COMMON.beeKnightSpawnWeight.get();
        }

        if (entity == ModEntities.BELLSNICKEL.get()) {
            return ModConfig.COMMON.bellsnickelSpawnWeight.get();
        }

        if (entity == ModEntities.SHROOMLING.get()) {
            return ModConfig.COMMON.shroomlingSpawnWeight.get();
        }

        return 0;
    }


    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ModBiomeModifierSerializers.FEYWILD_SPAWN_CODEC.get();
    }
}
