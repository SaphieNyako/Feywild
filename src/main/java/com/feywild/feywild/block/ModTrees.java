package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.trees.*;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;

@RegisterClass
public class ModTrees {

    public static final BaseTreeGrower springTree = new SpringTreeGrower(FeywildMod.getInstance());
    public static final BaseTreeGrower summerTree = new SummerTreeGrower(FeywildMod.getInstance());
    public static final BaseTreeGrower autumnTree = new AutumnTreeGrower(FeywildMod.getInstance());
    public static final BaseTreeGrower winterTree = new WinterTreeGrower(FeywildMod.getInstance());
}
