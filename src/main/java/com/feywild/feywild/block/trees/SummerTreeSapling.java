package com.feywild.feywild.block.trees;

import net.minecraft.block.Blocks;

public class SummerTreeSapling extends BaseSapling {

    public SummerTreeSapling() {
        super(SummerTree::new, Properties.from(Blocks.OAK_SAPLING));
    }
}
