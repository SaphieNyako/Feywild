package com.saphienyako.feywild.worldgen.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class FeyTreeProcessor extends StructureProcessor {
    public static final FeyTreeProcessor INSTANCE = new FeyTreeProcessor();
    public static final MapCodec<FeyTreeProcessor> MAP_CODEC =
            MapCodec.unit(() -> INSTANCE);

    @Override
    protected StructureProcessorType<?> getType() {
        return FeywildProcessors.FEY_TREE.get();
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos offset, BlockPos pos, StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo current, StructurePlaceSettings setting, @Nullable StructureTemplate template) {
        BlockState state = current.state();

        // Replace oak log
        if (state.is(Blocks.OAK_LOG)) {
            return new StructureTemplate.StructureBlockInfo(
                    current.pos(),
                    ModBlocks.AUTUMN_TREE_LOG.get().defaultBlockState()
                            .setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)),
                    current.nbt()
            );
            //TODO SCARRED LOG
        }

        // Replace oak leaves
        if (state.is(Blocks.OAK_LEAVES)) {
            return new StructureTemplate.StructureBlockInfo(
                    current.pos(),
                    ModBlocks.AUTUMN_TREE_LEAVES.get().defaultBlockState(),
                    current.nbt()
            );
        }

        if (state.is(Blocks.OAK_WOOD)) {
            return new StructureTemplate.StructureBlockInfo(
                    current.pos(),
                    ModBlocks.AUTUMN_TREE_WOOD.get().defaultBlockState(),
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
}
