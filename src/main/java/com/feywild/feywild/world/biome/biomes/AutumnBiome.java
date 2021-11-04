package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.world.biome.ModConfiguredSurfaceBuilders;
import com.feywild.feywild.world.structure.ModConfiguredStructures;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.biome.VanillaBiomes;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;

public class AutumnBiome implements BiomeType {

    public static final AutumnBiome INSTANCE = new AutumnBiome();

    private AutumnBiome() {

    }

    @Override
    public Biome.BiomeCategory category() {
        return Biome.BiomeCategory.MUSHROOM;
    }

    @Override
    public float scale() {
        return WorldGenConfig.biomes.autumn.size();
    }

    @Override
    public float temperature() {
        return 0.8f;
    }

    @Override
    public float downfall() {
        return 0.9f;
    }

    @Override
    public ConfiguredSurfaceBuilder<?> surface() {
        return ModConfiguredSurfaceBuilders.AUTUMN_SURFACE;
    }

    @Override
    public void ambience(BiomeSpecialEffects.Builder builder) {
        builder.waterColor(0x617b64);
        builder.waterFogColor(0x232317);
        builder.fogColor(0xc0d8ff);
        builder.skyColor(VanillaBiomes.calculateSkyColor(0.8f));
        if (ClientConfig.biome_music) {
            builder.backgroundMusic(new Music(ModSoundEvents.autumnSoundtrack, 6000, 12000, false));
        }
        builder.foliageColorOverride(0x6a7039);
        builder.ambientParticle(new AmbientParticleSettings(ParticleTypes.WITCH, 0.001f));
        builder.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.SWAMP);
    }

    @Override
    public void spawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 20, 2, 3));
    }

    @Override
    public void generation(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_LARGE_FERN);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.HUGE_RED_MUSHROOM);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.HUGE_BROWN_MUSHROOM);
        BiomeDefaultFeatures.addMushroomFieldVegetation(builder);
        builder.addStructureStart(ModConfiguredStructures.CONFIGURED_AUTUMN_WORLD_TREE);
    }

    @Override
    public void overworldSpawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 20, 4, 4));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 50, 1, 3));
    }

    @Override
    public void overworldGen(BiomeGenerationSettings.Builder builder) {
        builder.addStructureStart(StructureFeatures.SWAMP_HUT);
    }
}
