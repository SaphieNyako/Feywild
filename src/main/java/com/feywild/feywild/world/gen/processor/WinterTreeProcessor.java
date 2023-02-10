package com.feywild.feywild.world.gen.processor;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.FeyCrackedLogBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WinterTreeProcessor extends StructureProcessor {

    public static final WinterTreeProcessor INSTANCE = new WinterTreeProcessor();
    public static final Codec<WinterTreeProcessor> CODEC = Codec.unit(() -> {
        return INSTANCE;
    });

    public WinterTreeProcessor() {

    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(@Nonnull LevelReader worldReader, @Nonnull BlockPos jigsawPiecePos, @Nonnull BlockPos jigsawPieceBottomCenterPos, @Nonnull StructureTemplate.StructureBlockInfo blockInfoLocal, @Nonnull StructureTemplate.StructureBlockInfo blockInfoGlobal, @Nonnull StructurePlaceSettings structurePlacementData, @Nonnull StructureTemplate template) {
        ChunkPos currentChunkPos = new ChunkPos(blockInfoGlobal.pos);
        ChunkAccess currentChunk = worldReader.getChunk(currentChunkPos.x, currentChunkPos.z);
        RandomSource random = structurePlacementData.getRandom(blockInfoGlobal.pos);


        if (!(currentChunk.getBlockState(blockInfoGlobal.pos).getBlock() instanceof LeavesBlock) && currentChunk.getBlockState(blockInfoGlobal.pos).getBlock() != Blocks.AIR) {
            return null;
        }

        if (blockInfoGlobal.state.getBlock() == ModTrees.winterTree.getLogBlock()) {

            if (random.nextDouble() < 0.1) {
                currentChunk.setBlockState(blockInfoGlobal.pos, ModTrees.winterTree.getCrackedLogBlock().defaultBlockState().setValue(FeyCrackedLogBlock.CRACKED, random.nextInt(FeyCrackedLogBlock.CRACKED.getPossibleValues().size()) + 1), false);
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos, ModTrees.winterTree.getCrackedLogBlock().defaultBlockState().setValue(FeyCrackedLogBlock.CRACKED, random.nextInt(FeyCrackedLogBlock.CRACKED.getPossibleValues().size()) + 1), blockInfoGlobal.nbt);
            }

            //TODO ADD SNOW
        }

        return blockInfoGlobal;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return FeywildMod.WINTER_TREE_PROCESSOR;
    }
}
