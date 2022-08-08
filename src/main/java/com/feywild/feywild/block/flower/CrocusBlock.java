package com.feywild.feywild.block.flower;

import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.moddingx.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nonnull;
import java.util.Random;

public class CrocusBlock extends GiantFlowerBlock {

    public static final IntegerProperty OPENING_STATE = IntegerProperty.create("opening_state", 0, 2);

    public CrocusBlock(ModX mod) {
        super(mod, 3);
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPENING_STATE);
    }

    @Override
    protected void tickFlower(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isNight()) {
            level.setBlock(pos, state.setValue(OPENING_STATE, 0), 2);
        } else if (random.nextDouble() <= 0.4) {
            level.setBlock(pos, state.setValue(OPENING_STATE, 1 + random.nextInt(2)), 2);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void animateFlower(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.isNight()) {
            level.addParticle(ParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10);
        } else {
            level.addParticle(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 10);
        }
    }

    @Override
    public BlockState flowerState(LevelAccessor level, BlockPos pos, RandomSource random) {
        if (level instanceof Level) {
            if (((Level) level).isNight()) {
                return this.defaultBlockState().setValue(OPENING_STATE, 0);
            } else {
                return this.defaultBlockState().setValue(OPENING_STATE, 1 + random.nextInt(2));
            }
        } else {
            return this.defaultBlockState();
        }
    }
}
