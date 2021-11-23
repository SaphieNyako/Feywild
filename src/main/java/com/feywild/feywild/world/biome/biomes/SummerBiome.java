package com.feywild.feywild.world.biome.biomes;

import com.feywild.feywild.config.MobConfig;
import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.world.biome.ModConfiguredSurfaceBuilders;
import com.feywild.feywild.world.structure.ModConfiguredStructures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public class SummerBiome implements BiomeType {

    public static final SummerBiome INSTANCE = new SummerBiome();

    private SummerBiome() {

    }

    @Override
    public Biome.Category category() {
        return Biome.Category.SAVANNA;
    }

    @Override
    public Biome.RainType rain() {
        return Biome.RainType.NONE;
    }

    @Override
    public float scale() {
        return WorldGenConfig.biomes.summer.size;
    }

    @Override
    public float temperature() {
        return 0.9f;
    }

    @Override
    public float downfall() {
        return 0;
    }

    @Override
    public ConfiguredSurfaceBuilder<?> surface() {
        return ModConfiguredSurfaceBuilders.SUMMER_SURFACE;
    }

    @Override
    public void ambience(BiomeAmbience.Builder builder) {
        builder.waterColor(0x3f76e4);
        builder.waterFogColor(0x50533);
        builder.fogColor(0xc0d8ff);
        builder.skyColor(BiomeMaker.calculateSkyColor(0.9f));
        //builder.backgroundMusic(new BackgroundMusicSelector(ModSoundEvents.summerSoundtrack, 6000, 12000, false));

        builder.ambientParticle(new ParticleEffectAmbience(ParticleTypes.CRIT, 0.001f));
    }

    @Override
    public void spawns(MobSpawnInfo.Builder builder) {
        builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.BEE, 20, 2, 3));
        builder.addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.beeKnight,
                MobConfig.summer_bee_knight.weight, MobConfig.summer_bee_knight.min, MobConfig.summer_bee_knight.max));
    }

    @Override
    public void generation(BiomeGenerationSettings.Builder builder) {
        DefaultBiomeFeatures.addSavannaGrass(builder);
        DefaultBiomeFeatures.addForestFlowers(builder);
        DefaultBiomeFeatures.addSavannaExtraGrass(builder);
        DefaultBiomeFeatures.addJungleExtraVegetation(builder);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUNFLOWER);
        DefaultBiomeFeatures.addExtraGold(builder);
        builder.addStructureStart(ModConfiguredStructures.CONFIGURED_BEEKEEP);
        builder.addStructureStart(ModConfiguredStructures.CONFIGURED_SUMMER_WORLD_TREE);
    }

    @Override
    public void overworldSpawns(MobSpawnInfo.Builder builder) {
        builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.PILLAGER, 3, 2, 5));
        DefaultBiomeFeatures.farmAnimals(builder);
    }

    @Override
    public void overworldGen(BiomeGenerationSettings.Builder builder) {
        builder.addStructureStart(StructureFeatures.PILLAGER_OUTPOST);
    }
}
