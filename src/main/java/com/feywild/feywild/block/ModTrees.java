package com.feywild.feywild.block;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.feature.trees.*;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;

@RegisterClass
public class ModTrees {

    public static final BaseTree springTree = new SpringTreeGrower(FeywildMod.getInstance());
    public static final BaseTree summerTree = new SummerTreeGrower(FeywildMod.getInstance());
    public static final BaseTree autumnTree = new AutumnTreeGrower(FeywildMod.getInstance());
    public static final BaseTree winterTree = new WinterTreeGrower(FeywildMod.getInstance());
}
