package com.saphienyako.feywild.block;

import com.saphienyako.feywild.config.ModConfig;

import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


import javax.annotation.Nonnull;
import java.util.Random;

public abstract class GiantFlowerBlock extends Block {
    public static final VoxelShape STEM_SHAPE =
            box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape FLOWER_SHAPE =
            box(1, 0, 1, 15, 15, 15);

    // 0 - 2 = stem, 3 = flower
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 3);
    public final int height;

    public GiantFlowerBlock(int height) {
        super(Properties.copy(Blocks.LARGE_FERN).noCollission().sound(SoundType.BAMBOO).strength(1,1).lightLevel(value -> 8));
        this.height = height;
        this.registerDefaultState(this.getStateDefinition().any().setValue(PART, 3));
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public PushReaction getPistonPushReaction(@Nonnull BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state,@Nonnull IBlockReader reader,@Nonnull BlockPos pos,@Nonnull ISelectionContext context) {
        return state.getValue(PART) == 3 ? FLOWER_SHAPE : STEM_SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getVisualShape( @Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return state.getValue(PART) == 1 || state.getValue(PART) == 3 ? BlockRenderType.MODEL : BlockRenderType.INVISIBLE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState oldState,@Nonnull World level,@Nonnull BlockPos pos, BlockState newState, boolean moving) {
        if (oldState.getBlock() != newState.getBlock()) {
            this.removeOthers(level, oldState, pos);
        }
        super.onRemove(oldState, level, pos, newState, moving);
    }

    @Override
    public boolean isRandomlyTicking(@Nonnull BlockState state) {
        // Only tick flower head
        return state.getValue(PART) == 3;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerWorld level, @Nonnull BlockPos pos, @Nonnull Random random) {
        super.randomTick(state, level, pos, random);
        if (state.getValue(PART) == 3) this.tickFlower(state, level, pos, random);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull World level, @Nonnull BlockPos pos, @Nonnull Random random) {
        super.animateTick(state, level, pos, random);
        if (state.getValue(PART) == 3 && ModConfig.CLIENT.flower_particles.get()) this.animateFlower(state, level, pos, random);
    }

    protected abstract void tickFlower(BlockState state, ServerWorld level, BlockPos pos, Random random);

    @OnlyIn(Dist.CLIENT)
    protected abstract void animateFlower(BlockState state, World level, BlockPos pos, Random random);

    public abstract BlockState flowerState(IWorld world, BlockPos pos, Random random);

    protected void removeOthers(World level, BlockState state, BlockPos pos) {
        int blocksBelow = state.getValue(PART) - (4 - this.height);
        int blocksAbove = 3 - state.getValue(PART);

        for (int i = 1; i <= blocksBelow; i++) {
            BlockPos target = pos.offset(0, -i, 0);
            if (level.getBlockState(target).getBlock() == this) {
                // No block update
                level.setBlock(target, Blocks.AIR.defaultBlockState(), 2);
            }
        }

        for (int i = 1; i <= blocksAbove; i++) {
            BlockPos target = pos.offset(0, i, 0);
            if (level.getBlockState(target).getBlock() == this) {
                // No block update
                level.setBlock(target, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }
}
