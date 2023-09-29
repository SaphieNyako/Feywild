package com.feywild.feywild.world.gen.template;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;

public interface TemplatePlacementAction {
    
    void act(ServerLevelAccessor level, StructureTemplate template, StructurePlaceSettings settings, BlockPos start, List<StructureTemplate.StructureBlockInfo> processedBlocks, RandomSource random);
}
