package com.saphienyako.feywild.block.trees;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.worldgen.processor.AutumnTreeProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

import java.util.ArrayList;
import java.util.List;

public class AutumnTreeSaplingBlock extends FeySaplingBlock{

    private static final List<ResourceLocation> TREES = new ArrayList<>();
    static {
        for (int i = 0; i <= 4; i++) {
            TREES.add(new ResourceLocation(Feywild.MOD_ID, "fey_tree_" + i));
        }
    }
    public AutumnTreeSaplingBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected StructureProcessor getProcessor() {
        return AutumnTreeProcessor.SAPLING;
    }

    @Override
    protected List<ResourceLocation> getTrees() {
        return TREES;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, boolean b) {
        return true;
    }
}
