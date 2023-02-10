package com.feywild.feywild.block.trees;

import com.feywild.feywild.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import org.moddingx.libx.base.BlockBase;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.SetupContext;
import org.moddingx.libx.util.data.TagAccess;

import javax.annotation.Nonnull;

public class FeyLeavesBlock extends BlockBase implements Registerable, IForgeShearable {


    //TODO this can be normal leaves now?
    public static final int MAX_DISTANCE = 15;
    public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 0, MAX_DISTANCE);

    public static final int MAX_PARTICLE_DISTANCE = 48;

    private final int chance;
    private final SimpleParticleType particle;

    public FeyLeavesBlock(ModX mod, int chance, SimpleParticleType particle) {
        super(mod, BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS)
                .noOcclusion().isValidSpawn((s, r, p, t) -> false).isSuffocating((s, r, p) -> false).isViewBlocking((s, r, p) -> false));
        this.chance = chance;
        this.particle = particle;

        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 0).setValue(BlockStateProperties.PERSISTENT, false));
    }

    protected static BlockState updateDistance(BlockState state, LevelAccessor levelIn, BlockPos pos) {
        int distance = MAX_DISTANCE;
        BlockPos.MutableBlockPos current = new BlockPos.MutableBlockPos();
        for (Direction direction : Direction.values()) {
            if (distance > 1) {
                current.setWithOffset(pos, direction);
                distance = Math.min(distance, getDistance(levelIn.getBlockState(current)) + 1);
            }
        }
        return state.setValue(DISTANCE, distance);
    }

    private static int getDistance(BlockState neighbor) {
        if (TagAccess.ROOT.has(BlockTags.LOGS, neighbor.getBlock())) {
            return 0;
        } else if (neighbor.getBlock() instanceof FeyLeavesBlock) {
            return neighbor.getValue(DISTANCE);
        } else {
            return MAX_DISTANCE;
        }
    }

    @Override
    public void registerCommon(SetupContext ctx) {
        ctx.enqueue(() -> ComposterBlock.add(0.4f, this));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, BlockStateProperties.PERSISTENT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateDistance(this.defaultBlockState().setValue(BlockStateProperties.PERSISTENT, true), context.getLevel(), context.getClickedPos());
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getBlockSupportShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(DISTANCE) == MAX_DISTANCE && !state.getValue(BlockStateProperties.PERSISTENT);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
        updateDistance(state, level, pos);
        if (!state.getValue(BlockStateProperties.PERSISTENT) && state.getValue(DISTANCE) == MAX_DISTANCE) {
            dropResources(state, level, pos);
            level.removeBlock(pos, false);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(@Nonnull BlockState state, ServerLevel levelIn, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        levelIn.setBlock(pos, updateDistance(state, levelIn, pos), 3);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(@Nonnull BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor level, @Nonnull BlockPos pos, @Nonnull BlockPos facingPos) {
        int distance = getDistance(facingState) + 1;
        if (distance != 1 || state.getValue(DISTANCE) != distance) {
            level.scheduleTick(pos, this, 1);
        }
        return state;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        if (ClientConfig.tree_particles) {
            // Don't add particles if the blocks are far away
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.player.position().distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < MAX_PARTICLE_DISTANCE * MAX_PARTICLE_DISTANCE) {
                animateLeaves(state, level, pos, rand);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void animateLeaves(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        if (rand.nextInt(15) == 0 && level.isRainingAt(pos.above())) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                double x = pos.getX() + rand.nextDouble();
                double y = pos.getY() - 0.05;
                double z = pos.getZ() + rand.nextDouble();
                level.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0, 0, 0);
            }
        }
        if (rand.nextInt(chance) == 1 && level.isEmptyBlock(pos.below())) {
            level.addParticle(particle, pos.getX() + rand.nextDouble(), pos.getY(), pos.getZ() + rand.nextDouble(), 1, -0.1, 0);
        }
    }
}
