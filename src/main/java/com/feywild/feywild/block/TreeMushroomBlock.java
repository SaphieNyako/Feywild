package com.feywild.feywild.block;

import io.github.noeppi_noeppi.libx.block.DirectionShape;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockBase;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TreeMushroomBlock extends BlockBase {

    public static final DirectionShape SHAPE = new DirectionShape(VoxelShapes.or(
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
        super(mod, AbstractBlock.Properties.of(Material.GRASS)
                .sound(SoundType.FUNGUS)
                .noOcclusion().noCollission()
                .lightLevel(value -> 10)
        );

        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE.getShape(state.getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }
}
