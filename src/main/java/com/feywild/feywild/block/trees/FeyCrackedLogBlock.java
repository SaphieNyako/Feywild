package com.feywild.feywild.block.trees;

import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FeyCrackedLogBlock extends RotatedPillarBlock {

    //TODO Particles
    public static final int MAX_DISTANCE = 15;
    public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 0, MAX_DISTANCE);
    public static final int MAX_PARTICLE_DISTANCE = 48;

    public static final IntegerProperty CRACKED = IntegerProperty.create("cracked", 1, 6);
    private final FeyStrippedLogBlock strippedLog;
    private final SimpleParticleType particle;

    public FeyCrackedLogBlock(FeyStrippedLogBlock strippedLog, Properties properties, SimpleParticleType particle) {
        super(properties);
        this.strippedLog = strippedLog;
        this.particle = particle;
        this.registerDefaultState(this.stateDefinition.any().setValue(CRACKED, 1).setValue(DISTANCE, 0));
    }

    protected int getCrackedState(BlockState state) {
        return state.getValue(this.getCrackedProperty());
    }

    public IntegerProperty getCrackedProperty() {
        return CRACKED;
    }


    @Override
    public @Nonnull
    BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return toolAction == ToolActions.AXE_STRIP ? strippedLog.defaultBlockState().setValue(AXIS, state.getValue(AXIS)) : Objects.requireNonNull(super.getToolModifiedState(state, context, toolAction, simulate));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if (player.getItemInHand(hand).getItem() == ModItems.feyDust) {
            if (!level.isClientSide) {
                int i = this.getCrackedState(state);
                if (i < 5) {
                    state = state.setValue(CRACKED, i + 1);
                    level.setBlock(pos, state, 2);
                    if (player.isCreative()) {
                        player.getItemInHand(hand).shrink(1);
                    }
                } else {
                    state = state.setValue(CRACKED, 1);
                    level.setBlock(pos, state, 2);
                    if (player.isCreative()) {
                        player.getItemInHand(hand).shrink(1);
                    }
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CRACKED).add(DISTANCE).add(AXIS);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        if (ClientConfig.tree_particles) {
            // Don't add particles if the blocks are far away
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.player.position().distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < MAX_PARTICLE_DISTANCE * MAX_PARTICLE_DISTANCE) {
                animateParticles(state, level, pos, rand);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void animateParticles(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {

        level.addParticle(particle, pos.getX() + (Math.random() - 0.5),
                pos.getY() + 1 + (Math.random() - 0.5),
                pos.getZ() + (Math.random() - 0.5),
                0, 0, 0);

    }
}
