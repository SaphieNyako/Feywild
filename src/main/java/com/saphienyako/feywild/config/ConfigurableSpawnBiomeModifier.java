package com.saphienyako.feywild.config;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.worldgen.ModBiomeModifierSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;

public record ConfigurableSpawnBiomeModifier(HolderSet<Biome> biomes, EntityType<?> entity) implements BiomeModifier {

    @Override
    public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
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
            return FeywildConfig.autumnPixieSpawnWeight;
        }

        if (entity == ModEntities.SPRING_PIXIE.get()) {
            return FeywildConfig.springPixieSpawnWeight;
        }

        if (entity == ModEntities.SUMMER_PIXIE.get()) {
            return FeywildConfig.summerPixieSpawnWeight;
        }

        if (entity == ModEntities.WINTER_PIXIE.get()) {
            return FeywildConfig.winterPixieSpawnWeight;
        }

        if (entity == ModEntities.MANDRAGORA.get()) {
            return FeywildConfig.mandragoraSpawnWeight;
        }

        if (entity == ModEntities.BEE_MOUNT.get()) {
            return FeywildConfig.beeKnightSpawnWeight;
        }

        if (entity == ModEntities.BELLSNICKEL.get()) {
            return FeywildConfig.bellsnickelSpawnWeight;
        }

        if (entity == ModEntities.SHROOMLING.get()) {
            return FeywildConfig.shroomlingSpawnWeight;
        }

        return 0;
    }


    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return ModBiomeModifierSerializers.FEYWILD_SPAWN.get();
    }
}
