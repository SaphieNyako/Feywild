package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.Block;

public class SpringTree extends BaseTree {

    @Override
    protected Block getLogBlock() {
        return ModBlocks.SPRING_TREE_LOG.get();
    }

    @Override
    protected Block getLeafBlock() {
        return ModBlocks.SPRING_TREE_LEAVES.get();
    }

}
