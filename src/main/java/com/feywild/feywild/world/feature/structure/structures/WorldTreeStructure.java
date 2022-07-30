package com.feywild.feywild.world.feature.structure.structures;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.feature.structure.load.StructureUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.CheckerboardColumnBiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

import javax.annotation.Nonnull;
import java.util.NoSuchElementException;
import java.util.Optional;

import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier.Context;

public class WorldTreeStructure extends StructureFeature<JigsawConfiguration> {

    public WorldTreeStructure(String structureId) {
        super(JigsawConfiguration.CODEC, new WorldTreeStructure.PlacementFactory(structureId));
    }

    public static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        ChunkPos chunkpos = context.chunkPos();
        BiomeSource biomeSource = context.biomeSource();
        if (!(biomeSource instanceof CheckerboardColumnBiomeSource)) {
            for (int x = chunkpos.x - 4; x <= chunkpos.x + 4; ++x) {
                for (int z = chunkpos.z - 4; z <= chunkpos.z + 4; ++z) {
                    int y = context.chunkGenerator().getFirstFreeHeight(x << 4, z << 4, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
                    Holder<Biome> biome = biomeSource.getNoiseBiome(x << 2, y >> 2, z << 2, context.chunkGenerator().climateSampler());
                    if (!context.validBiome().test(biome)) {
                        return false;
                    }
                }
            }
        }
        //TODO STRUCTURE CHECK NEARBY
        return true;
    }

    @Nonnull
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    private static class PlacementFactory implements PieceGeneratorSupplier<JigsawConfiguration> {

        private final String structureId;

        private PlacementFactory(String structureId) {
            this.structureId = structureId;
        }

        @Nonnull
        @Override
        public Optional<PieceGenerator<JigsawConfiguration>> createGenerator(@Nonnull Context<JigsawConfiguration> context) {
            if (!isFeatureChunk(context)) {
                return Optional.empty();
            }

            BlockPos centerOfChunk = context.chunkPos().getMiddleBlockPosition(0);

            int landHeight = context.chunkGenerator().getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(),
                    Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

            NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ(), context.heightAccessor());

            BlockState topBlock = columnOfBlocks.getBlock(landHeight);

            if (!topBlock.getFluidState().isEmpty()) return Optional.empty();

            JigsawConfiguration config = new JigsawConfiguration(
                    context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                            .getHolder(ResourceKey.create(Registry.TEMPLATE_POOL_REGISTRY, FeywildMod.getInstance().resource(this.structureId)))
                            .orElseThrow(() -> new NoSuchElementException("Template not found: " + this.structureId)), 10
            );

            return JigsawPlacement.addPieces(
                    StructureUtils.withConfig(context, config), PoolElementStructurePiece::new,
                    context.chunkPos().getMiddleBlockPosition(0), false, true
            );
        }
    }
}
