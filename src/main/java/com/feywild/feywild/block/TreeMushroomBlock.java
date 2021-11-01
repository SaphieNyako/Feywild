package com.feywild.feywild.block;

import io.github.noeppi_noeppi.libx.base.BlockBase;
import io.github.noeppi_noeppi.libx.block.RotationShape;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TreeMushroomBlock extends BlockBase {

    public static final RotationShape SHAPE = new RotationShape(Shapes.or(
            box(10, 10.4375, 14, 13, 11.1875, 16),
            box(0, 8.25, 10, 7, 9.9375, 16),
            box(1.25, 7.875, 11.125, 5.75, 8.25, 16),
            box(8.625, 4.375, 12.75, 11.5625, 4.75, 16),
            box(6.125, 12.375, 12.75, 9.0625, 12.75, 16),
            box(10.625, 10.0625, 14.4375, 12.625, 10.4375, 16),
            box(7.5, 4.75, 12, 12.5, 5.75, 16),
            box(5.0625, 12.75, 12, 10.0625, 13.75, 16)
    ));

    public TreeMushroomBlock(ModX mod) {
        super(mod, BlockBehaviour.Properties.of(Material.GRASS)
                .sound(SoundType.FUNGUS)
                .noOcclusion().noCollission()
                .lightLevel(value -> 10)
        );

        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE.getShape(state.getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(@Nonnull BlockState state, @Nonnull Rotation rot) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rot.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(@Nonnull BlockState state, @Nonnull Mirror mirror) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, mirror.mirror(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }
}
