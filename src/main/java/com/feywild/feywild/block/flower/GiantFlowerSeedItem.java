package com.feywild.feywild.block.flower;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;

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
        
        for (int i = 0; i < 4; i++) {
            if (!world.getBlockState(pos.above(i)).canBeReplaced(blockContext)) {
                return ActionResultType.PASS;
            }
        }

        if (!world.isClientSide) {
            world.setBlock(pos, this.block.defaultBlockState().setValue(GiantFlowerBlock.PART, 4 - this.block.height), 3);
            if (this.block.height >= 2) world.setBlock(pos.above(1), this.block.defaultBlockState().setValue(GiantFlowerBlock.PART, 5 - this.block.height), 3);
            if (this.block.height >= 3) world.setBlock(pos.above(2), this.block.defaultBlockState().setValue(GiantFlowerBlock.PART, 6 - this.block.height), 3);
            if (this.block.height >= 4) world.setBlock(pos.above(3), this.block.flowerState(world, pos.above(3), world.random).setValue(GiantFlowerBlock.PART, 7 - this.block.height), 3);
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) context.getItemInHand().shrink(1);
        }
        return ActionResultType.sidedSuccess(world.isClientSide);
    }
}
