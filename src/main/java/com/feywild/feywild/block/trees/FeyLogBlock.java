package com.feywild.feywild.block.trees;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FeyLogBlock extends RotatedPillarBlock implements ILogBlock {

    private final FeyWoodBlock feyWood;
    private final FeyStrippedLogBlock strippedLog;

    public FeyLogBlock(FeyWoodBlock feyWood, FeyStrippedLogBlock strippedLog, Properties properties) {
        super(properties);
        this.feyWood = feyWood;
        this.strippedLog = strippedLog;
    }

    @Override
    public FeyWoodBlock getWoodBlock() {
        return feyWood;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return toolAction == ToolActions.AXE_STRIP ? strippedLog.defaultBlockState().setValue(AXIS, state.getValue(AXIS)) : super.getToolModifiedState(state, context, toolAction, simulate);
    }
}

