package com.feywild.feywild.world.gen.processor;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.FeyCrackedLogBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AutumnTreeProcessor extends StructureProcessor {

    public static final AutumnTreeProcessor INSTANCE = new AutumnTreeProcessor();
    public static final Codec<AutumnTreeProcessor> CODEC = Codec.unit(() -> {
        return INSTANCE;
    });

    public AutumnTreeProcessor() {

    }

    private static BlockState getDecorationBlock(RandomSource random) {
        return switch (random.nextInt(20)) {
            case 0 -> Blocks.PUMPKIN.defaultBlockState();
            case 1 -> Blocks.CARVED_PUMPKIN.defaultBlockState();
            case 2 -> Blocks.RED_MUSHROOM.defaultBlockState();
            case 3 -> Blocks.BROWN_MUSHROOM.defaultBlockState();
            default -> Blocks.FERN.defaultBlockState();
        };
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

        if (blockInfoGlobal.state.getBlock() == ModTrees.autumnTree.getLogBlock()) {

            if (random.nextDouble() < 0.1) {
                currentChunk.setBlockState(blockInfoGlobal.pos, ModTrees.autumnTree.getCrackedLogBlock().defaultBlockState().setValue(FeyCrackedLogBlock.CRACKED, random.nextInt(FeyCrackedLogBlock.CRACKED.getPossibleValues().size()) + 1), false);
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos, ModTrees.autumnTree.getCrackedLogBlock().defaultBlockState().setValue(FeyCrackedLogBlock.CRACKED, random.nextInt(FeyCrackedLogBlock.CRACKED.getPossibleValues().size()) + 1), blockInfoGlobal.nbt);
                return blockInfoGlobal;
            }

        }
        //Instead of adding which cause weird spawn, I remove them from the trees.
        if (blockInfoGlobal.state.getBlock() == ModBlocks.treeMushroom) {

            if (random.nextDouble() < 0.95) {
                currentChunk.setBlockState(blockInfoGlobal.pos, Blocks.AIR.defaultBlockState(), false);
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos, Blocks.AIR.defaultBlockState(), blockInfoGlobal.nbt);
                return blockInfoGlobal;
            }

        }

        return blockInfoGlobal;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return FeywildMod.AUTUMN_TREE_PROCESSOR;
    }
}
