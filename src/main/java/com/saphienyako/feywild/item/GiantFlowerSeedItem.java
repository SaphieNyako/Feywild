package com.saphienyako.feywild.item;

import com.saphienyako.feywild.block.GiantFlowerBlock;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class GiantFlowerSeedItem extends Item {
    private final GiantFlowerBlock block;

    public GiantFlowerSeedItem(GiantFlowerBlock block) {
        super(new Item.Properties().tab(ModCreativeModeTab.FEYWILD_TAB));
        this.block = block;
    }

    public static void placeFlower(GiantFlowerBlock block, IWorld level, BlockPos pos, Random random, int placeFlags) {
        for (int i = 0; i < block.height; i++) {
          BlockState baseState = (i == block.height - 1) ? block.flowerState(level, pos.above(i), random) : block.defaultBlockState();
            level.setBlock(pos.above(i), baseState.setValue(GiantFlowerBlock.PART, i + (4 - block.height)), placeFlags);
        }
    }

    @Nonnull
    @Override
    public ActionResultType useOn(@Nonnull ItemUseContext context) {
        World level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (!level.getBlockState(pos).getMaterial().isReplaceable()) {
            pos = pos.above();
        }

        BlockState soil = level.getBlockState(pos.below());

        //for (int i = 0; i < this.block.height; i++) {
        if (!soil.canSustainPlant(level, pos.below(), Direction.UP, (IPlantable) this.block)) {
            return ActionResultType.PASS;
        }

        for (int i = 0; i < this.block.height; i++) {
            if (!level.getBlockState(pos.above(i)).getMaterial().isReplaceable()) {
                return ActionResultType.PASS;
            }
        }

        if (!level.isClientSide) {
            placeFlower(this.block, level, pos, level.random, 3);

            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1);
            }
        }

        return ActionResultType.sidedSuccess(level.isClientSide);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World level,@Nonnull List<ITextComponent> tooltip,@Nonnull ITooltipFlag flag) {
        if (level != null) {
            if(this.block == ModBlocks.GIANT_SUN_FLOWER.get()) {
                TranslationTextComponent text = new TranslationTextComponent("message.feywild.giant_sun_flower");
                text.getStyle().withColor(Color.fromLegacyFormat(TextFormatting.BLUE));
                tooltip.add(text);
            }
            if(this.block == ModBlocks.GIANT_CROCUS_FLOWER.get()) {
                TranslationTextComponent text = new TranslationTextComponent("message.feywild.giant_crocus_flower");
                text.getStyle().withColor(Color.fromLegacyFormat(TextFormatting.BLUE));
                tooltip.add(text);
            }
            if(this.block == ModBlocks.GIANT_DANDELION_FLOWER.get()) {
                TranslationTextComponent text = new TranslationTextComponent("message.feywild.giant_sun_flower");
                text.getStyle().withColor(Color.fromLegacyFormat(TextFormatting.BLUE));
                tooltip.add(text);
            }
        }

        super.appendHoverText(stack,level,tooltip,flag);
    }
}
