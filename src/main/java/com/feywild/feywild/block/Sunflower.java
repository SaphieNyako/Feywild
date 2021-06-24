package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class Sunflower extends Block {

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant",0,2);

    public Sunflower() {
        super(Properties.of(Material.PLANT).harvestTool(ToolType.AXE).sound(SoundType.BAMBOO).randomTicks().strength(1,1));
        this.registerDefaultState(this.stateDefinition.any().setValue(VARIANT, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_149653_1_) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos p_225542_3_, Random p_225542_4_) {
        if(world.getDayTime() < 2800){
            world.setBlock(p_225542_3_,state.setValue(VARIANT,0),2,1);
        }else if(world.getDayTime() < 8400){
            world.setBlock(p_225542_3_,state.setValue(VARIANT,1),2,1);
        }else{
            world.setBlock(p_225542_3_,state.setValue(VARIANT,2),2,1);
        }
        super.randomTick(state, world, p_225542_3_, p_225542_4_);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);

    }
}
