package com.feywild.feywild.block.flower;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class GiantFlowerSeedItem extends ItemBase {
    
    private final GiantFlowerBlock block;
    
    public GiantFlowerSeedItem(ModX mod, GiantFlowerBlock block) {
        super(mod, new Item.Properties());
        this.block = block;
    }

    @Nonnull
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockItemUseContext blockContext = new BlockItemUseContext(context);
        BlockPos pos = context.getClickedPos();
        if (!world.getBlockState(pos).canBeReplaced(blockContext)) pos = pos.above();
        
        if (!Tags.Blocks.DIRT.contains(world.getBlockState(pos.below()).getBlock())) {
            return ActionResultType.PASS;
        }
        
        for (int i = 0; i < this.block.height; i++) {
            if (!world.getBlockState(pos.above(i)).canBeReplaced(blockContext)) {
                return ActionResultType.PASS;
            }
        }

        if (!world.isClientSide) {
            placeFlower(this.block, world, pos, world.random, 3);
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) context.getItemInHand().shrink(1);
        }
        return ActionResultType.sidedSuccess(world.isClientSide);
    }
    
    public static void placeFlower(GiantFlowerBlock block, IWorld world, BlockPos pos, Random random, int placeFlags) {
        for (int i = 0; i < block.height; i++) {
            BlockState baseState = (i == block.height - 1) ? block.flowerState(world, pos, random) : block.defaultBlockState();
            world.setBlock(pos.above(1), baseState.setValue(GiantFlowerBlock.PART, i + (4 - block.height)), placeFlags);

        }
    }
}
