package com.feywild.feywild;

import com.feywild.feywild.block.trees.BaseTree;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FeyRegistries {
    
    public static final ResourceKey<Registry<BaseTree>> TREES = ResourceKey.createRegistryKey(FeywildMod.getInstance().resource("trees"));
}
