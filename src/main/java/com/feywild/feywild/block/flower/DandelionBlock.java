package com.feywild.feywild.block.flower;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.ParticleSerializer;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class DandelionBlock extends GiantFlowerBlock {

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 3);

    public DandelionBlock(ModX mod) {
        super(mod, 4);
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT);
    }

    @Override
    protected void tickFlower(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.getValue(VARIANT) == 3 && world.random.nextInt(3) == 0) {
            world.setBlock(pos, state.setValue(VARIANT, 2), 3);
        }
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (this.replaceFlower(world, pos.above(3 - state.getValue(PART)))) {
            if (!world.isClientSide && player instanceof ServerPlayerEntity) {
                // Forge notifies the client of the block break before calling this
                // So we just tell the client that the block is still there
                ((ServerPlayerEntity) player).connection.send(new SChangeBlockPacket(world, pos));
            }
            return false;
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    private boolean replaceFlower(@Nonnull World world, @Nonnull BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == this && state.getValue(PART) == 3 && state.getValue(VARIANT) == 2) {
            if (!world.isClientSide) {
                world.setBlock(pos, state.setValue(VARIANT, 3), 3);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRemove(@Nonnull BlockState oldState, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean moving) {
        super.onRemove(oldState, world, pos, newState, moving);
        if (oldState.getValue(VARIANT) == 2) {
            FeywildMod.getNetwork().sendParticles(world, ParticleSerializer.Type.DANDELION_FLUFF, pos);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getDestroyProgress(@Nonnull BlockState state, @Nonnull PlayerEntity player, @Nonnull IBlockReader world, @Nonnull BlockPos pos) {
        return state.getValue(PART) == 3 && state.getValue(VARIANT) == 2 ? 1 : super.getDestroyProgress(state, player, world, pos);
    }

    @Override
    protected void animateFlower(BlockState state, World world, BlockPos pos, Random random) {
        if (state.getValue(VARIANT) == 2 && random.nextDouble() < 0.4) {
            double windStrength = Math.cos((double) world.getGameTime() / 2000) / 8;
            double windX = Math.cos((double) world.getGameTime() / 1200) * windStrength;
            double windZ = Math.sin((double) world.getGameTime() / 1000) * windStrength;
            world.addParticle(ParticleTypes.END_ROD, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), windX, 0, windZ);
        }
    }

    @Override
    public BlockState flowerState(IWorld world, BlockPos pos, Random random) {
        return this.defaultBlockState().setValue(VARIANT, random.nextInt(3));
    }
}
