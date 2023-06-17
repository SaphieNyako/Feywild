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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class SpringTreeProcessor extends StructureProcessor {

    public static final SpringTreeProcessor INSTANCE = new SpringTreeProcessor();
    public static final Codec<SpringTreeProcessor> CODEC = Codec.unit(() -> {
        return INSTANCE;
    });

    public SpringTreeProcessor() {
    }

    private static BlockState getDecorationBlock(RandomSource random) {
        return switch (random.nextInt(10)) {
            case 0 -> Blocks.RED_TULIP.defaultBlockState();
            case 1 -> Blocks.DANDELION.defaultBlockState();
            case 2 -> Blocks.ORANGE_TULIP.defaultBlockState();
            case 3 -> Blocks.BLUE_ORCHID.defaultBlockState();
            case 4 -> Blocks.ALLIUM.defaultBlockState();
            case 5 -> Blocks.AZURE_BLUET.defaultBlockState();
            case 6 -> Blocks.WHITE_TULIP.defaultBlockState();
            case 7 -> Blocks.LILY_OF_THE_VALLEY.defaultBlockState();
            default -> Blocks.GRASS.defaultBlockState();
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

        if (blockInfoGlobal.state.getBlock() == ModTrees.springTree.getLogBlock()) {

            if (random.nextDouble() < 0.1) {
                currentChunk.setBlockState(blockInfoGlobal.pos, ModTrees.springTree.getCrackedLogBlock().defaultBlockState().setValue(FeyCrackedLogBlock.CRACKED, random.nextInt(FeyCrackedLogBlock.CRACKED.getPossibleValues().size()) + 1), false);
                blockInfoGlobal = new StructureTemplate.StructureBlockInfo(blockInfoGlobal.pos, ModTrees.springTree.getCrackedLogBlock().defaultBlockState().setValue(FeyCrackedLogBlock.CRACKED, random.nextInt(FeyCrackedLogBlock.CRACKED.getPossibleValues().size()) + 1), blockInfoGlobal.nbt);
            }
        }

        //TODO add spore blossom
        /*
        if (blockInfoGlobal.state.getBlock() == ModBlocks.springCyanLeaves || blockInfoGlobal.state.getBlock() == ModBlocks.springLimeLeaves || blockInfoGlobal.state.getBlock() == ModBlocks.springGreenLeaves) {
            if (random.nextDouble() < 0.2) {
                BlockPos.MutableBlockPos mutable = blockInfoGlobal.pos.below().mutable();
                BlockState blockMutable = worldReader.getBlockState(mutable);
                if (blockMutable.getBlock() == Blocks.AIR) {
                    currentChunk.setBlockState(mutable, Blocks.SPORE_BLOSSOM.defaultBlockState(), false);
                    blockInfoGlobal = new StructureTemplate.StructureBlockInfo(mutable, Blocks.SPORE_BLOSSOM.defaultBlockState(), blockInfoGlobal.nbt);
                    return blockInfoGlobal;
                }
            }
        }*/

        return blockInfoGlobal;
    }


    @Override
    protected StructureProcessorType<?> getType() {
        return FeywildMod.SPRING_TREE_PROCESSOR;
    }

}
