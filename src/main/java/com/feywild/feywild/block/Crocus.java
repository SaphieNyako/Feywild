package com.feywild.feywild.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
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

import javax.annotation.Nonnull;
import java.util.Random;

public class Crocus extends Block {

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 2);
    public boolean isNight;
    //3 crocus variants

    public Crocus() {
        super(Properties.of(Material.PLANT).harvestTool(ToolType.AXE).sound(SoundType.BAMBOO).randomTicks().strength(1, 1).lightLevel(value -> 8));
        this.registerDefaultState(this.stateDefinition.any().setValue(VARIANT, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }

    @Override
    public boolean isRandomlyTicking(@Nonnull BlockState state) {
        return true;
    }

    //Crocus close at night and bad weather
    //0 closed
    //1 a bit open
    //2 open

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (world.isNight()) {
            isNight = true;
            world.setBlock(pos, state.setValue(VARIANT, 0), 2);
        } else if (random.nextDouble() <= 0.4) {
            world.setBlock(pos, state.setValue(VARIANT, random.nextInt(2) + 1), 2);
            isNight = false;
        }

        super.randomTick(state, world, pos, random);
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {

        if (isNight) {
            world.addParticle(ParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10);

        } else {
            world.addParticle(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10);
        }

        super.animateTick(state, world, pos, random);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);
    }

}
