package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.world.feature.trees.*;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "")
public class ModTrees {

    public static final BaseTree springTree = new SpringTree(FeywildMod.getInstance());
    public static final BaseTree summerTree = new SummerTree(FeywildMod.getInstance());
    public static final BaseTree autumnTree = new AutumnTree(FeywildMod.getInstance());
    public static final BaseTree winterTree = new WinterTree(FeywildMod.getInstance());
}
