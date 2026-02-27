package com.saphienyako.feywild.worldgen.processor;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class FeyTreeProcessor extends StructureProcessor {



    @Override
    protected StructureProcessorType<?> getType() {
        return FeywildProcessors.AUTUMN_TREE.get();
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo current, StructurePlaceSettings setting, @Nullable StructureTemplate template) {
        BlockState state = current.state();

        // Replace oak log
        if (state.is(Blocks.OAK_LOG)) {
            RandomSource random = setting.getRandom(current.pos());

            BlockState logState =
                    random.nextInt(20) == 0
                            ? getCrackedLogBlock().defaultBlockState()
                            : getLogBlock().defaultBlockState();

            return new StructureTemplate.StructureBlockInfo(
                    current.pos(),
                    logState.setValue(
                            RotatedPillarBlock.AXIS,
                            state.getValue(RotatedPillarBlock.AXIS)
                    ),
                    current.nbt()
            );
        }

        // Replace oak leaves
        if (state.is(Blocks.OAK_LEAVES)) {
            RandomSource random = setting.getRandom(pos);

            List<Block> leaves = getLeafVariants();
            Block chosenLeaves = leaves.get(random.nextInt(leaves.size()));

            return new StructureTemplate.StructureBlockInfo(
                    current.pos(),
                    chosenLeaves.defaultBlockState(),
                    current.nbt()
            );
        }

        if (state.is(Blocks.OAK_WOOD)) {
            return new StructureTemplate.StructureBlockInfo(
                    current.pos(),
                    getWoodBlock().defaultBlockState(),
                    current.nbt()
            );
        }

        if (state.is(Blocks.AIR)) {
            return new StructureTemplate.StructureBlockInfo(
                    current.pos(),
                    Blocks.STRUCTURE_VOID.defaultBlockState(),
                    current.nbt()
            );
        }

        return current;
    }

    abstract protected Block getLogBlock();
    abstract protected Block getCrackedLogBlock();

    protected abstract List<Block> getLeafVariants();

    abstract protected Block getWoodBlock();
}
