package com.feywild.feywild.world.structure.structures;

import com.feywild.feywild.config.data.StructureData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;

import javax.annotation.Nonnull;

import net.minecraft.world.level.levelgen.feature.StructureFeature.StructureStartFactory;

public abstract class BaseStructure extends StructureFeature<NoneFeatureConfiguration> {

    public BaseStructure() {
        super(NoneFeatureConfiguration.CODEC);
    }
    
    public abstract StructureData getStructureData();
    public abstract String getStructureId();
    public abstract int getSeedModifier();

    public final StructureFeatureConfiguration getSettings() {
        return new StructureFeatureConfiguration(
                getStructureData().average_distance(),
                getStructureData().minimum_distance(),
                getSeedModifier()
        );
    }
    
    @Nonnull
    @Override
    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return (feature, chunkPos, references, seed) -> new BaseStart(feature, chunkPos, references, seed, this.getStructureId());
    }

    @Nonnull
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator generator, @Nonnull BiomeSource biomeSource, long seed, @Nonnull WorldgenRandom random, ChunkPos chunkPos, @Nonnull Biome biome, @Nonnull ChunkPos potentialPos, @Nonnull NoneFeatureConfiguration config, @Nonnull LevelHeightAccessor level) {
        BlockPos centerOfChunk = new BlockPos((chunkPos.x << 4) + 7, 0, (chunkPos.z << 4) + 7);
        int landHeight = generator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG, level);
        NoiseColumn columnOfBlocks = generator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ(), level);
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
        return topBlock.getFluidState().isEmpty();
    }
}
