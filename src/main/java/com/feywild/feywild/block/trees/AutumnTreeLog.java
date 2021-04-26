package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class AutumnTreeLog extends Block {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty GROWN = BooleanProperty.create("grown_by_sapling");

    public AutumnTreeLog() {
        super(AbstractBlock.Properties.from(Blocks.JUNGLE_WOOD));

        this.setDefaultState(this.stateContainer.getBaseState()
                .with(GROWN, true)
                .with(AXIS, Direction.Axis.Y));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder builder) {

        builder.add(GROWN, AXIS);

    }

    //WHEN PLACED BY PLAYER SHOULD BE FALSE.

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(GROWN, Boolean.valueOf(false))
                .with(AXIS, context.getFace().getAxis());
    }


    public BlockState rotate(BlockState state, Rotation rot) {
        switch (rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch ((Direction.Axis) state.get(AXIS)) {
                    case X:
                        return state.with(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.with(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }



    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {

        Random rand = new Random();

        if (!worldIn.isRemote && pos.getY() >= 68 && state.get(SummerTreeLog.GROWN))
            if (rand.nextDouble() < 0.05) {

                if (worldIn.isAirBlock(pos.north())) {
                    //This works
                    worldIn.setBlockState(pos.north(), ModBlocks.TREE_MUSHROOM_BLOCK.get().getDefaultState());
                    return;
                }

                if (worldIn.isAirBlock(pos.east())) {

                    Rotation rotation = Rotation.CLOCKWISE_90;
                    //This works
                    worldIn.setBlockState(pos.east(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().getDefaultState(), rotation));
                    return;
                }

                if (worldIn.isAirBlock(pos.south())) {

                    Rotation rotation = Rotation.CLOCKWISE_180;
                    //this one breaks sometimes
                    worldIn.setBlockState(pos.south(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().getDefaultState(), rotation));
                    return;
                }

                if (worldIn.isAirBlock(pos.west())) {

                    Rotation rotation = Rotation.COUNTERCLOCKWISE_90;
                    //this one breaks sometimes
                    worldIn.setBlockState(pos.west(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().getDefaultState(), rotation));
                    return;
                }
            }
    }
}