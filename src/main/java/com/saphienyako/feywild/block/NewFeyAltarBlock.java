package com.saphienyako.feywild.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class NewFeyAltarBlock extends Block {

    public static final VoxelShape BOTTOM_SHAPE = box(0, 0, 0, 16, 16, 16);
    public static final VoxelShape UPPER_SHAPE = box(0, 0, 0, 16, 16, 16);
    // 0 - 1 = 2 parts
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 1);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public NewFeyAltarBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(3f, 10f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, 0)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public int getLightEmission(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART, FACING);
    }

    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(PART) == 1 ? UPPER_SHAPE : BOTTOM_SHAPE;
    }

    @Override
    public @NotNull VoxelShape getVisualShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            level.setBlock(
                    pos.above(),
                    this.defaultBlockState()
                            .setValue(PART, 1)
                            .setValue(FACING, state.getValue(FACING)),
                    3
            );
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        if (!level.getBlockState(pos.above()).canBeReplaced(context)) {
            return null;
        }

        return this.defaultBlockState()
                .setValue(PART, 0)
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        int part = state.getValue(PART);

        if (part == 0 && direction == Direction.UP) {
            if (neighborState.getBlock() != this || neighborState.getValue(PART) != 1) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        if (part == 1 && direction == Direction.DOWN) {
            if (neighborState.getBlock() != this || neighborState.getValue(PART) != 0) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }


    @Override
    public void onRemove(BlockState oldState, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean moving) {
        if (oldState.getBlock() != newState.getBlock()) {
            this.removeOthers(level, oldState, pos);
        }
        super.onRemove(oldState, level, pos, newState, moving);
    }

    protected void removeOthers(Level level, BlockState state, BlockPos pos) {
        BlockPos otherPos;

        if (state.getValue(PART) == 0) {
            // Bottom part -> remove upper
            otherPos = pos.above();
        } else {
            // Upper part -> remove bottom
            otherPos = pos.below();
        }

        BlockState otherState = level.getBlockState(otherPos);

        if (otherState.getBlock() == this) {
            level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 2);
        }
    }

}
