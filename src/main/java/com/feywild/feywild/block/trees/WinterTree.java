package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.Block;

public class WinterTree extends BaseTree {

    @Override
    protected Block getLogBlock() {
        return ModBlocks.WINTER_TREE_LOG.get();
    }

    @Override
    protected Block getLeafBlock() {
        return ModBlocks.WINTER_TREE_LEAVES.get();
    }

}
