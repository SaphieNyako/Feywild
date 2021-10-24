package com.feywild.feywild.world.structure.structures;

import com.feywild.feywild.FeywildMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import javax.annotation.Nonnull;

public class BaseStart extends StructureStart<NoneFeatureConfiguration> {

    private final String poolId;
    
    public BaseStart(StructureFeature<NoneFeatureConfiguration> feature, ChunkPos chunkPos, int references, long seed, String poolId) {
        super(feature, chunkPos, references, seed);
        this.poolId = poolId;
    }

    @Override
    public void generatePieces(@Nonnull RegistryAccess registryAccess, @Nonnull ChunkGenerator chunkGenerator, @Nonnull StructureManager structureManager, @Nonnull ChunkPos chunkPos, @Nonnull Biome biome, @Nonnull NoneFeatureConfiguration config, @Nonnull LevelHeightAccessor level) {
        // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
        int x = (chunkPos.x << 4) + 7;
        int z = (chunkPos.z << 4) + 7;
        BlockPos blockpos = new BlockPos(x, 0, z);
        JigsawPlacement.addPieces(
                registryAccess,
                new JigsawConfiguration(() -> registryAccess.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                        .get(FeywildMod.getInstance().resource(this.poolId)),
                        10
                ),
                PoolElementStructurePiece::new, chunkGenerator, structureManager, blockpos,
                this, this.random, false, true, level
        );
        this.pieces.forEach(piece -> piece.getBoundingBox().inflate(1));
    }
}