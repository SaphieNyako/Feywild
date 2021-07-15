package com.feywild.feywild.block.trees;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Supplier;

public abstract class BaseSapling extends BushBlock implements IGrowable {

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;

    private Tree tree;

    public BaseSapling(Supplier<Tree> tree, Properties properties) {
        super(properties);

        this.tree = tree.get();
    }

    @Override
    public boolean isValidBonemealTarget(@Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        //50% chance of Bonemeal working.
        return (double) worldIn.random.nextFloat() < 0.50;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(@Nonnull BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        super.tick(state, worldIn, pos, rand);
        if (!worldIn.isAreaLoaded(pos, 1)) {
            return;
        }
        //attempt to grow
        if (worldIn.getMaxLocalRawBrightness(pos.above()) >= 9 && rand.nextInt(7) == 0) {
            this.performBonemeal(worldIn, rand, pos, state);
        }
    }

    @Override
    public void performBonemeal(@Nonnull ServerWorld worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, BlockState state) {
        if (state.getValue(STAGE) == 0) {

            worldIn.setBlock(pos, state.cycle(STAGE), 4); //state.cycle
        } else {
            if (!ForgeEventFactory.saplingGrowTree(worldIn, rand, pos)) {
                return;
            }
            this.tree.growTree(worldIn, worldIn.getChunkSource().getGenerator(), pos, state, rand);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
