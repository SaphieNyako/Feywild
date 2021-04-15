package com.feywild.feywild.block.trees;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;

public class WinterTreeSapling extends BaseSapling {

    public WinterTreeSapling() {
        super(WinterTree::new, AbstractBlock.Properties.from(Blocks.OAK_SAPLING));
    }
}
