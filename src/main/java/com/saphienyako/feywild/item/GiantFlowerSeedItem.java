package com.saphienyako.feywild.item;

import com.saphienyako.feywild.block.GiantFlowerBlock;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

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

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPlaceContext blockContext = new BlockPlaceContext(context);
        BlockPos pos = context.getClickedPos();
        if (!level.getBlockState(pos).canBeReplaced(blockContext)) pos = pos.above();

        if (!(Objects.requireNonNull(ForgeRegistries.BLOCKS.tags())).getTag(BlockTags.DIRT).contains(level.getBlockState(pos.below()).getBlock())) {
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
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) {
            if(Screen.hasShiftDown()){
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

            else {
                tooltip.add(Component.translatable("message.feywild.shift_down").withStyle(ChatFormatting.GREEN));
            }


        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
