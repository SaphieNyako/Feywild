package com.feywild.feywild.block.flower;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.ParticleSerializer;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class Dandelion extends GiantFlowerBlock {

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 3);

    public Dandelion(ModX mod) {
        super(mod, 4);
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT);
    }

    @Override
    protected void tickFlower(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        
    }

    @Override
    public void onRemove(@Nonnull BlockState oldState, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean moving) {
        super.onRemove(oldState, world, pos, newState, moving);
        if (oldState.getValue(VARIANT) == 2) {
            FeywildMod.getNetwork().sendParticles(world, ParticleSerializer.Type.DANDELION_FLUFF, pos);
        }
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
