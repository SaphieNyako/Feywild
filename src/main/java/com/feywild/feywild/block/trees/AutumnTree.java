package com.feywild.feywild.block.trees;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.Block;

public class AutumnTree extends BaseTree {

    @Override
    protected Block getLogBlock() {
        return ModBlocks.AUTUMN_TREE_LOG.get();
    }

    @Override
    protected Block getLeafBlock() {
        return ModBlocks.AUTUMN_TREE_LEAVES.get();
    }

}
