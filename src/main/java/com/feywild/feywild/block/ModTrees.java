package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.block.trees.SpringTree;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;

@RegisterClass
public class ModTrees {

    public static final BaseTree springTree = new SpringTree(FeywildMod.getInstance());
    public static final BaseTree summerTree = new SpringTree(FeywildMod.getInstance());
    public static final BaseTree autumnTree = new SpringTree(FeywildMod.getInstance());
    public static final BaseTree winterTree = new SpringTree(FeywildMod.getInstance());
}
