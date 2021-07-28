package com.feywild.feywild.block.trees;

import com.feywild.feywild.util.configs.Config;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import java.util.Random;

public class FeyLeavesBlock extends Block implements net.minecraftforge.common.IForgeShearable {

    public static final BooleanProperty PERSISTENT = BooleanProperty.create("persistent_leaves");
    private static final int maxDistance = 15;
    public static final IntegerProperty DISTANCE = IntegerProperty.create("more_distance", 0, maxDistance);

    public FeyLeavesBlock() {
        super(AbstractBlock.Properties.of(Material.LEAVES)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .harvestTool(ToolType.HOE)
                .noOcclusion()
                .isValidSpawn((s, r, p, t) -> false)
                .isSuffocating((s, r, p) -> false)
                .isViewBlocking((s, r, p) -> false));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(DISTANCE, 0)
                .setValue(PERSISTENT, false));
    }

    protected static BlockState updateDistance(BlockState state, IWorld worldIn, BlockPos pos) {

        int i = maxDistance;

        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for (Direction direction : Direction.values()) {
            blockpos$mutable.setWithOffset(pos, direction);
            i = Math.min(i, getDistance(worldIn.getBlockState(blockpos$mutable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return state.setValue(DISTANCE, i);
    }

    private static int getDistance(BlockState neighbor) {
        if (BlockTags.LOGS.contains(neighbor.getBlock())) {
            return 0;
        } else {

            return neighbor.getBlock() instanceof FeyLeavesBlock ? neighbor.getValue(DISTANCE) : maxDistance;
        }
    }

    @Nonnull
    @Override
    public VoxelShape getBlockSupportShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(DISTANCE) == maxDistance && !state.getValue(PERSISTENT);
    }

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random random) {

        updateDistance(state, worldIn, pos);

        if (!state.getValue(PERSISTENT) && state.getValue(DISTANCE) == maxDistance) {
            dropResources(state, worldIn, pos);
            worldIn.removeBlock(pos, false);
        }
    }

    @Override
    public void tick(@Nonnull BlockState state, ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        worldIn.setBlock(pos, updateDistance(state, worldIn, pos), 3);
    }

    @Override
    public int getLightBlock(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos) {
        return 1;
    }

    @Nonnull
    @Override
    public BlockState updateShape(@Nonnull BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        int i = getDistance(facingState) + 1;
        if (i != 1 || stateIn.getValue(DISTANCE) != i) {
            worldIn.getBlockTicks().scheduleTick(currentPos, this, 1);
        }

        return stateIn;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (Config.PERFORMANCE_CONFIG.cachedTreeParticlesValue()) {
            if (worldIn.isRainingAt(pos.above())) {
                if (rand.nextInt(15) == 1) {
                    BlockPos blockpos = pos.below();
                    BlockState blockstate = worldIn.getBlockState(blockpos);
                    if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(worldIn, blockpos, Direction.UP)) {
                        double d0 = (double) pos.getX() + rand.nextDouble();
                        double d1 = (double) pos.getY() - 0.05D;
                        double d2 = (double) pos.getZ() + rand.nextDouble();
                        worldIn.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                    }
                }
            }

            if (worldIn.isEmptyBlock(pos.below()) && rand.nextInt(30) == 1) {
                double windStrength = 5 + Math.cos((double) worldIn.getGameTime() / 2000) * 2;
                double windX = Math.cos((double) worldIn.getGameTime() / 1200) * windStrength;
                double windZ = Math.sin((double) worldIn.getGameTime() / 1000) * windStrength;

                worldIn.addParticle(new BlockParticleData(ParticleTypes.BLOCK, stateIn), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, windX, -1.0, windZ);

            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return updateDistance(this.defaultBlockState().setValue(PERSISTENT, true), context.getLevel(), context.getClickedPos());
    }

}
