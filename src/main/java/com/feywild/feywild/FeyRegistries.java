package com.feywild.feywild;

import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.entity.ability.Ability;
import com.feywild.feywild.world.gen.template.TemplatePlacementAction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FeyRegistries {
    
    public static final ResourceKey<Registry<BaseTree>> TREES = ResourceKey.createRegistryKey(FeywildMod.getInstance().resource("trees"));
    public static final ResourceKey<Registry<Ability<?>>> ABILITIES = ResourceKey.createRegistryKey(FeywildMod.getInstance().resource("abilities"));
    public static final ResourceKey<Registry<TemplatePlacementAction>> TEMPLATE_ACTIONS = ResourceKey.createRegistryKey(FeywildMod.getInstance().resource("template_placement_actions"));
}
