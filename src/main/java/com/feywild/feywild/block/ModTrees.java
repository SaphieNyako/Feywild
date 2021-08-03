package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.trees.*;
import io.github.noeppi_noeppi.libx.annotation.RegisterClass;

@RegisterClass
public class ModTrees {

    public static final BaseTree springTree = new SpringTree(FeywildMod.getInstance());
    public static final BaseTree summerTree = new SummerTree(FeywildMod.getInstance());
    public static final BaseTree autumnTree = new AutumnTree(FeywildMod.getInstance());
    public static final BaseTree winterTree = new WinterTree(FeywildMod.getInstance());
}
