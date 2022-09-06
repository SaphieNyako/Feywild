package com.feywild.feywild.block.flower;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.base.ItemBase;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GiantFlowerSeedItem extends ItemBase {

    private final GiantFlowerBlock block;

    public GiantFlowerSeedItem(ModX mod, GiantFlowerBlock block) {
        super(mod, new Item.Properties());
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
}
