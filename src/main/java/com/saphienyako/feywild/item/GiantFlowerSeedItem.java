package com.saphienyako.feywild.item;

import com.saphienyako.feywild.block.GiantFlowerBlock;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GiantFlowerSeedItem extends Item {
    private final GiantFlowerBlock block;

    public GiantFlowerSeedItem(GiantFlowerBlock block) {
        super(new Item.Properties());
        this.block = block;
    }

    public static void placeFlower(GiantFlowerBlock block, LevelAccessor level, BlockPos pos, RandomSource random, int placeFlags) {
        for (int i = 0; i < block.height; i++) {
            BlockState baseState = (i == block.height - 1) ? block.flowerState(level, pos.above(i), random) : block.defaultBlockState();
            level.setBlock(pos.above(i), baseState.setValue(GiantFlowerBlock.PART, i + (4 - block.height)), placeFlags);
        }
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPlaceContext blockContext = new BlockPlaceContext(context);
        BlockPos pos = context.getClickedPos();
        if (!level.getBlockState(pos).canBeReplaced(blockContext)) pos = pos.above();

        if (!level.getBlockState(pos.below()).is(BlockTags.DIRT)){ //TODO is this correct?
            return InteractionResult.PASS;
        }

        for (int i = 0; i < this.block.height; i++) {
            if (!level.getBlockState(pos.above(i)).canBeReplaced(blockContext)) {
                return InteractionResult.PASS;
            }
        }

        if (!level.isClientSide) {
            placeFlower(this.block, level, pos, level.random, 3);
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) context.getItemInHand().shrink(1);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(this.block == ModBlocks.GIANT_SUN_FLOWER.get()) {
            tooltip.add(Component.translatable("message.feywild.giant_sun_flower").withStyle(ChatFormatting.BLUE));
        }
        if(this.block == ModBlocks.GIANT_CROCUS_FLOWER.get()) {
            tooltip.add(Component.translatable("message.feywild.giant_crocus_flower").withStyle(ChatFormatting.BLUE));
        }
        if(this.block == ModBlocks.GIANT_DANDELION_FLOWER.get()) {
            tooltip.add(Component.translatable("message.feywild.giant_sun_flower").withStyle(ChatFormatting.BLUE));
        }
    }
}
