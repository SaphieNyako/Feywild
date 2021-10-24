package com.feywild.feywild.world.structure.structures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;

import javax.annotation.Nonnull;

import net.minecraft.world.level.levelgen.feature.StructureFeature.StructureStartFactory;

public abstract class BaseStructure extends StructureFeature<NoneFeatureConfiguration> {

    public BaseStructure() {
        super(NoneFeatureConfiguration.CODEC);
    }

    public abstract int getAverageDistanceBetweenChunks();

    public abstract int getMinDistanceBetweenChunks();

    public abstract int getSeedModifier();

    public final StructureFeatureConfiguration getSettings() {
        return new StructureFeatureConfiguration(
                getAverageDistanceBetweenChunks(),
                getMinDistanceBetweenChunks(),
                getSeedModifier()
        );
    }
    
    @Nonnull
    @Override
    public abstract StructureStartFactory<NoneFeatureConfiguration> getStartFactory();

    @Nonnull
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    // Creatures methods are not always used

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, @Nonnull BiomeSource biomeSource, long seed, @Nonnull WorldgenRandom chunkRandom, int chunkX, int chunkZ, @Nonnull Biome biome, @Nonnull ChunkPos chunkPos, @Nonnull NoneFeatureConfiguration featureConfig) {
        BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);

        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG);

        BlockGetter columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());

        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));

        return topBlock.getFluidState().isEmpty();
    }
}
