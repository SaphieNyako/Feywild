package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.world.biome.ModConfiguredSurfaceBuilders;
import com.feywild.feywild.world.structure.ModConfiguredStructures;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.biome.VanillaBiomes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;

public class SpringBiome implements BiomeType {

    public static final SpringBiome INSTANCE = new SpringBiome();

    private SpringBiome() {

    }

    @Override
    public Biome.BiomeCategory category() {
        return Biome.BiomeCategory.FOREST;
    }

    @Override
    public float scale() {
        return WorldGenConfig.biomes.spring.size();
    }

    @Override
    public float temperature() {
        return 0.7f;
    }

    @Override
    public float downfall() {
        return 0.8f;
    }

    @Override
    public ConfiguredSurfaceBuilder<?> surface() {
        return ModConfiguredSurfaceBuilders.SPRING_SURFACE;
    }

    @Override
    public void ambience(BiomeSpecialEffects.Builder builder) {
        builder.waterColor(0x3f76e4);
        builder.waterFogColor(0x50533);
        builder.fogColor(0xc0d8ff);
        builder.skyColor(VanillaBiomes.calculateSkyColor(0.7f));
        //builder.backgroundMusic(new Music(ModSoundEvents.springSoundtrack, 6000, 12000, false));
        builder.ambientParticle(new AmbientParticleSettings(ParticleTypes.HAPPY_VILLAGER, 0.001f));
    }

    @Override
    public void generation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.JUNGLE_BUSH);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_WATER);
        BiomeDefaultFeatures.addExtraEmeralds(builder);
        builder.addStructureStart(ModConfiguredStructures.CONFIGURED_SPRING_WORLD_TREE);
    }

    @Override
    public void overworldSpawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 50, 1, 3));
        BiomeDefaultFeatures.farmAnimals(builder);
    }

    @Override
    public void overworldGen(BiomeGenerationSettings.Builder builder) {
        builder.addStructureStart(StructureFeatures.JUNGLE_TEMPLE);
    }
}
