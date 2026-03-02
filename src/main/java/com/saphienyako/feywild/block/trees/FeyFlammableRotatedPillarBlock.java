package com.saphienyako.feywild.block.trees;

import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;


import javax.annotation.Nonnull;

public class FeyFlammableRotatedPillarBlock extends RotatedPillarBlock {

    public FeyFlammableRotatedPillarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem) {
            Block stripped = getStripped(state.getBlock());
            if (stripped != null) {
                return stripped.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }
        return null;
    }

    @SuppressWarnings("deprecated")
    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state,@Nonnull Level level,@Nonnull BlockPos pos,@Nonnull Player player,@Nonnull InteractionHand hand,@Nonnull BlockHitResult hit) {
        if (player.getItemInHand(hand).getItem() == ModItems.FEY_GEM.get()) {
            Block cracked = getCracked(state.getBlock());
            if (!level.isClientSide && cracked != null ) {
                BlockState newState = cracked.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
                level.setBlock(pos, newState, 3);

                if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    private static Block getStripped(Block block) {
        if (block == ModBlocks.AUTUMN_TREE_LOG.get()) return ModBlocks.AUTUMN_TREE_STRIPPED_LOG.get();
        if (block == ModBlocks.AUTUMN_TREE_WOOD.get()) return ModBlocks.AUTUMN_TREE_STRIPPED_WOOD.get();
        if (block == ModBlocks.SPRING_TREE_LOG.get()) return ModBlocks.SPRING_TREE_STRIPPED_LOG.get();
        if (block == ModBlocks.SPRING_TREE_WOOD.get()) return ModBlocks.SPRING_TREE_STRIPPED_WOOD.get();
        if (block == ModBlocks.SUMMER_TREE_LOG.get()) return ModBlocks.SUMMER_TREE_STRIPPED_LOG.get();
        if (block == ModBlocks.SUMMER_TREE_WOOD.get()) return ModBlocks.SUMMER_TREE_STRIPPED_WOOD.get();
        if (block == ModBlocks.WINTER_TREE_LOG.get()) return ModBlocks.WINTER_TREE_STRIPPED_LOG.get();
        if (block == ModBlocks.WINTER_TREE_WOOD.get()) return ModBlocks.WINTER_TREE_STRIPPED_WOOD.get();
        return null;
    }

    private static Block getCracked(Block block) {
        if (block == ModBlocks.AUTUMN_TREE_LOG.get()) return ModBlocks.AUTUMN_TREE_CRACKED_LOG.get();
        if (block == ModBlocks.SPRING_TREE_LOG.get()) return ModBlocks.SPRING_TREE_CRACKED_LOG.get();
        if (block == ModBlocks.SUMMER_TREE_LOG.get()) return ModBlocks.SUMMER_TREE_CRACKED_LOG.get();
        if (block == ModBlocks.WINTER_TREE_LOG.get()) return ModBlocks.WINTER_TREE_CRACKED_LOG.get();
        return null;
    }
}
