package com.saphienyako.feywild.block;

import com.saphienyako.feywild.config.FeywildConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public abstract class GiantFlowerBlock extends Block {
    public static final VoxelShape STEM_SHAPE = box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape FLOWER_SHAPE = box(1, 0, 1, 15, 15, 15);
    // 0 - 2 = stem, 3 = flower
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 3);
    public final int height;

    public GiantFlowerBlock(int height) {
        super(Properties.ofFullCopy(Blocks.LARGE_FERN).noOcclusion().sound(SoundType.BAMBOO).strength(1, 1).lightLevel(value -> 8).pushReaction(PushReaction.DESTROY));
        this.height = height;
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, 3));
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART);
    }

    @Override

    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(PART) == 3 ? FLOWER_SHAPE : STEM_SHAPE;
    }

    @Override

    public @NotNull VoxelShape getVisualShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == 1 || state.getValue(PART) == 3 ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }

    @Override
    public void onRemove(BlockState oldState, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean moving) {
        if (oldState.getBlock() != newState.getBlock()) {
            this.removeOthers(level, oldState, pos);
        }
        super.onRemove(oldState, level, pos, newState, moving);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        // Only tick flower head
        return state.getValue(PART) == 3;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (state.getValue(PART) == 3) this.tickFlower(state, level, pos, random);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (state.getValue(PART) == 3 && FeywildConfig.flowerParticles) this.animateFlower(state, level, pos, random);
    }

    protected abstract void tickFlower(BlockState state, ServerLevel world, BlockPos pos, RandomSource random);

    @OnlyIn(Dist.CLIENT)
    protected abstract void animateFlower(BlockState state, Level world, BlockPos pos, RandomSource random);

    public abstract BlockState flowerState(LevelAccessor world, BlockPos pos, RandomSource random);

    protected void removeOthers(Level level, BlockState state, BlockPos pos) {
        int blocksBelow = state.getValue(PART) - (4 - this.height);
        int blocksAbove = 3 - state.getValue(PART);

        for (int i = 1; i <= blocksBelow; i++) {
            BlockPos target = pos.offset(0, -i, 0);
            if (level.getBlockState(target).getBlock() == this) {
                level.setBlock(target, Blocks.AIR.defaultBlockState(), 2);
            }
        }

        for (int i = 1; i <= blocksAbove; i++) {
            BlockPos target = pos.offset(0, i, 0);
            if (level.getBlockState(target).getBlock() == this) {
                level.setBlock(target, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }
}
