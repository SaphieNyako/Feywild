package com.feywild.feywild.world.dimension.feywild.setup;

import com.feywild.feywild.world.dimension.feywild.FeywildDimension;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSamplingSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class FeywildChunkGenerator {

    public static final Codec<Generator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryOps.retrieveRegistry(Registry.STRUCTURE_SET_REGISTRY).forGetter(generator -> generator.structureSets),
            RegistryOps.retrieveRegistry(Registry.NOISE_REGISTRY).forGetter(generator -> generator.noises),
            RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter(s -> s.biomeRegistry),
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource),
            Codec.LONG.fieldOf("seed").orElse(0L).forGetter(generator -> generator.seed)
            // Don't serialise the noise generator settings as they are not present in the registry
            // Adding it to the codec would corrupt level.dat
    ).apply(instance, instance.stable(Generator::new)));

    private static NoiseGeneratorSettings generatorSettings() {
        NoiseGeneratorSettings settings = NoiseGeneratorSettings.bootstrap().value();
        //noinspection deprecation
        return new NoiseGeneratorSettings(
                withSampling(settings.noiseSettings()),
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                settings.noiseRouter(),
                FeywildDimension.buildFeywildSurface(),
                settings.seaLevel(),
                settings.disableMobGeneration(),
                settings.isAquifersEnabled(),
                true,
                settings.useLegacyRandomSource()
        );
    }

    private static NoiseSettings withSampling(NoiseSettings noiseSettings) {
        NoiseSamplingSettings noiseSamplingSettings = new NoiseSamplingSettings(0.6, 9, 80, 120);
        return new NoiseSettings(noiseSettings.minY(), noiseSettings.height(), noiseSamplingSettings, noiseSettings.topSlideSettings(), noiseSettings.bottomSlideSettings(),
                noiseSettings.noiseSizeHorizontal(), noiseSettings.noiseSizeVertical(), noiseSettings.terrainShaper());
    }

    private static class Generator extends NoiseBasedChunkGenerator {

        private final Registry<Biome> biomeRegistry;
        private final HolderSet<StructureSet> structureSettings;

        public Generator(Registry<StructureSet> structureSets, Registry<NormalNoise.NoiseParameters> noises, Registry<Biome> biomeRegistry, BiomeSource biomeSource, long seed) {
            super(structureSets, noises, biomeSource, seed, Holder.direct(generatorSettings()));
            this.biomeRegistry = biomeRegistry;
            this.structureSettings = HolderSet.direct(structureSets.holders().filter(FeywildDimension.buildFeywildStructures(biomeRegistry)).toList());

        }

        @Override
        protected @NotNull Codec<? extends ChunkGenerator> codec() {
            return FeywildChunkGenerator.CODEC;
        }

        @Override
        public @NotNull ChunkGenerator withSeed(long seed) {
            return new Generator(this.structureSets, this.noises, this.biomeRegistry, this.biomeSource, seed);
        }

        @Override
        public @NotNull Stream<Holder<StructureSet>> possibleStructureSets() {
            return this.structureSettings.stream();
        }

    }

}
