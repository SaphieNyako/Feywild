package com.feywild.feywild.world.gen.tree;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.world.gen.template.TemplatePlacementAction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;

public class MushroomAction implements TemplatePlacementAction {

    @Override
    public void act(ServerLevelAccessor level, StructureTemplate template, StructurePlaceSettings settings, BlockPos start, List<StructureTemplate.StructureBlockInfo> processedBlocks, RandomSource random) {
        for (StructureTemplate.StructureBlockInfo info : processedBlocks) {
            if (info.state().is(BlockTags.LOGS) && random.nextInt(5) == 0) {
                Direction dir = Direction.from2DDataValue(random.nextInt(4));
                if (level.isEmptyBlock(info.pos().relative(dir))) {
                    level.setBlock(info.pos().relative(dir), ModBlocks.treeMushroom.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir), 19);
                }
            }
        }
    }
}
