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
import javax.annotation.Nullable;

public class FeyCrackedLogBlock extends RotatedPillarBlock {

    public static final IntegerProperty CRACKED = IntegerProperty.create("cracked", 1, 6);
    
    private final FeyStrippedLogBlock strippedLog;
    private final SimpleParticleType particle;

    public FeyCrackedLogBlock(FeyStrippedLogBlock strippedLog, Properties properties, SimpleParticleType particle) {
        super(properties);
        this.strippedLog = strippedLog;
        this.particle = particle;
        this.registerDefaultState(this.stateDefinition.any().setValue(CRACKED, 1));
    }
    
    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CRACKED);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return toolAction == ToolActions.AXE_STRIP ? strippedLog.defaultBlockState().setValue(AXIS, state.getValue(AXIS)) : super.getToolModifiedState(state, context, toolAction, simulate);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if (player.getItemInHand(hand).getItem() == ModItems.feyDust) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.cycle(CRACKED), 3);
                if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        if (ClientConfig.tree_particles) {
            // Don't add particles if the blocks are far away
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.player.position().distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 48 * 48) {
                level.addParticle(particle, pos.getX() + (Math.random() - 0.5),
                        pos.getY() + 1 + (Math.random() - 0.5),
                        pos.getZ() + (Math.random() - 0.5),
                        0, 0, 0);
            }
        }
    }
}
