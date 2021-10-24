package com.feywild.feywild.block.trees;

import io.github.noeppi_noeppi.libx.base.decoration.DecoratedBlock;
import io.github.noeppi_noeppi.libx.base.decoration.DecorationContext;
import io.github.noeppi_noeppi.libx.base.decoration.DecorationType;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nonnull;

public class FeyWoodBlock extends DecoratedBlock {

    private static final DecorationContext DECORATION = new DecorationContext("fey_tree", DecorationType.BASE, DecorationType.FENCE, DecorationType.FENCE_GATE, DecorationType.SLAB, DecorationType.STAIR);
    
    public FeyWoodBlock(ModX mod, Properties properties) {
        super(mod, DECORATION, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
    }
    
    public SlabBlock getSlabBlock() {
        return get(DecorationType.SLAB);
    }

    public StairBlock getStairBlock() {
        return get(DecorationType.STAIR);
    }

    public FenceBlock getFenceBlock() {
        return get(DecorationType.FENCE);
    }

    public FenceGateBlock getFenceGateBlock() {
        return get(DecorationType.FENCE_GATE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.AXIS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.AXIS, context.getClickedFace().getAxis());
    }
    
    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(@Nonnull BlockState state, @Nonnull Rotation rot) {
        return RotatedPillarBlock.rotatePillar(state, rot);
    }
}
