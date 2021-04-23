package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

public class SummerTree extends BaseTree {

    @Override
    protected Block getLogBlock() {return ModBlocks.SUMMER_TREE_LOG.get();
    }

    @Override
    protected Block getLeafBlock() {
        return ModBlocks.SUMMER_TREE_LEAVES.get();
    }

}
