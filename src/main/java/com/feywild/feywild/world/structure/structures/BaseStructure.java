package com.feywild.feywild.world.structure.structures;

import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

import javax.annotation.Nonnull;

public abstract class BaseStructure extends Structure<NoFeatureConfig> {

    public BaseStructure() {
        super(NoFeatureConfig.CODEC);
    }

    public abstract int getAverageDistanceBetweenChunks();

    public abstract int getMinDistanceBetweenChunks();

    public abstract int getSeedModifier();

    public final StructureSeparationSettings getSettings() {
        return new StructureSeparationSettings(
                getAverageDistanceBetweenChunks(),
                getMinDistanceBetweenChunks(),
                getSeedModifier()
        );
    }
    
    @Nonnull
    @Override
    public abstract IStartFactory<NoFeatureConfig> getStartFactory();

    @Nonnull
    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    // Creatures methods are not always used

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, @Nonnull BiomeProvider biomeSource, long seed, @Nonnull SharedSeedRandom chunkRandom, int chunkX, int chunkZ, @Nonnull Biome biome, @Nonnull ChunkPos chunkPos, @Nonnull NoFeatureConfig featureConfig) {
        BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);

        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);

        IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());

        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));

        return topBlock.getFluidState().isEmpty();
    }
}
