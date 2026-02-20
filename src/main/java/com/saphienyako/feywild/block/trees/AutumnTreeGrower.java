package com.saphienyako.feywild.block.trees;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class AutumnTreeGrower {
    public static final TreeGrower AUTUMN_TREE_GROWER = new TreeGrower(Feywild.MOD_ID + ":autumn_tree_grower",
            Optional.empty(), Optional.of(ModConfiguredFeatures.AUTUMN_TREE_KEY), Optional.empty());
}
