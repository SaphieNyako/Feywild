package com.feywild.feywild.block.trees;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;

import javax.annotation.Nonnull;

// TODO let logs extends minecrafts own abstract log block
public class SummerTreeLog extends Block {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty GROWN = BooleanProperty.create("grown_by_sapling");

    public SummerTreeLog() {
        super(AbstractBlock.Properties.copy(Blocks.JUNGLE_WOOD));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(GROWN, true)
                .setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(GROWN, AXIS);
    }

    //WHEN PLACED BY PLAYER SHOULD BE FALSE.
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(GROWN, false)
                .setValue(AXIS, context.getClickedFace().getAxis());
    }

    @Nonnull
    @Override
    public BlockState rotate(@Nonnull BlockState state, Rotation rot) {
        switch (rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch (state.getValue(AXIS)) {
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }
}
