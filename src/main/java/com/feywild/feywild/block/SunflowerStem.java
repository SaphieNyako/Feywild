package com.feywild.feywild.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class SunflowerStem extends Block {

    public static final BooleanProperty HAS_MODEL = BooleanProperty.create("model");
    public SunflowerStem() {
        super(AbstractBlock.Properties.of(Material.PLANT).harvestTool(ToolType.AXE).noCollission().strength(1,1));
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_MODEL, false));
    }


    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HAS_MODEL);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);

    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        if(!p_149645_1_.getValue(HAS_MODEL))
        return BlockRenderType.INVISIBLE;
        return BlockRenderType.MODEL;
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState blockState, boolean p_196243_5_) {
        super.onRemove(state, world, pos, blockState, p_196243_5_);
        if(world.isClientSide) return;

        if(world.getBlockState(pos.above()).is(ModBlocks.SUNFLOWER_STEM.get())){
            world.destroyBlock(pos.above(),false);
        }
        if(world.getBlockState(pos.below()).is(ModBlocks.SUNFLOWER_STEM.get())){
            world.destroyBlock(pos.below(),false);
        }
        if(world.getBlockState(pos.above()).is(ModBlocks.SUNFLOWER.get())){
            world.destroyBlock(pos.above(),true);
        }
    }
}
