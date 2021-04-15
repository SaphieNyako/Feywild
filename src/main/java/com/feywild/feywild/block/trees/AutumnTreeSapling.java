package com.feywild.feywild.block.trees;

import net.minecraft.block.Blocks;

public class AutumnTreeSapling extends BaseSapling {

    public AutumnTreeSapling() {
        super(AutumnTree::new, Properties.from(Blocks.OAK_SAPLING));
    }
}
