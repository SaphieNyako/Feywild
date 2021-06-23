package com.feywild.feywild.block;

import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

public class Dandelion extends Block {

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant",0,3);

    public Dandelion() {
        super(Properties.of(Material.PLANT).harvestTool(ToolType.AXE).randomTicks().strength(1,1));
        this.registerDefaultState(this.stateDefinition.any().setValue(VARIANT, 1));
    }

    @Override
    public void randomTick(BlockState p_225542_1_, ServerWorld p_225542_2_, BlockPos pos, Random p_225542_4_) {
        super.randomTick(p_225542_1_, p_225542_2_, pos, p_225542_4_);
        if(p_225542_1_.getValue(VARIANT) == 2)
        FeywildPacketHandler.sendToPlayersInRange(p_225542_2_,pos, new ParticleMessage(pos.getX(),pos.getY(),pos.getZ(),0,0,0,5,2,0),64);
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
    public void onRemove(BlockState p_196243_1_, World world, BlockPos pos, BlockState p_196243_4_, boolean p_196243_5_) {
        super.onRemove(p_196243_1_, world, pos, p_196243_4_, p_196243_5_);
        if(world.isClientSide) return;


        if(p_196243_1_.getValue(VARIANT) == 2) {
            FeywildPacketHandler.sendToPlayersInRange(world, pos, new ParticleMessage(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.5, 0.7, 0.5, 40, -2, 0), 64);
            if(!world.getBlockState(pos.below()).isAir())
            world.setBlock(pos, ModBlocks.DANDELION.get().defaultBlockState().setValue(Dandelion.VARIANT,3), 2, 1);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);

    }
}
