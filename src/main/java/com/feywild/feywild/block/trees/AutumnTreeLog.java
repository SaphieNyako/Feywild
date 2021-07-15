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
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class AutumnTreeLog extends Block {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty GROWN = BooleanProperty.create("grown_by_sapling");

    public AutumnTreeLog() {
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

    @Nonnull
    @Override
    public BlockState updateShape(@Nonnull BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, IWorld worldIn, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {

        worldIn.getBlockTicks().scheduleTick(currentPos, this, 1);

        return stateIn;
    }
    
    // TODO i feel placing mushrooms here is the wrong way of doing it.
    // Could cause incompatibilities with other mods I think
    @Override
    public void onPlace(@Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean isMoving) {

        Random rand = new Random();

        if (!worldIn.isClientSide && state.getValue(AutumnTreeLog.GROWN))
            if (rand.nextDouble() < 0.05) {
                if (worldIn.isEmptyBlock(pos.north())) {

                    worldIn.setBlockAndUpdate(pos.north(), ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState());
                } else if (worldIn.isEmptyBlock(pos.east())) {

                    Rotation rotation = Rotation.CLOCKWISE_90;

                    worldIn.setBlockAndUpdate(pos.east(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState(), rotation));
                } else if (worldIn.isEmptyBlock(pos.south())) {

                    Rotation rotation = Rotation.CLOCKWISE_180;

                    worldIn.setBlockAndUpdate(pos.south(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState(), rotation));
                } else if (worldIn.isEmptyBlock(pos.west())) {

                    Rotation rotation = Rotation.COUNTERCLOCKWISE_90;

                    worldIn.setBlockAndUpdate(pos.west(), ModBlocks.TREE_MUSHROOM_BLOCK.get().rotate(ModBlocks.TREE_MUSHROOM_BLOCK.get().defaultBlockState(), rotation));
                }
            }
    }
}