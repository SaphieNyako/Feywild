package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.TreeEnt;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.resources.ResourceLocation;

public class TreeEntModel extends TypedEntityModel<TreeEnt> {

    public TreeEntModel(Alignment alignment) {
        this(alignment.id);
    }
    
    public TreeEntModel(String subType) {
        super("tree_ent", subType);
    }

    @Override
    public ResourceLocation buildFormattedAnimationPath(ResourceLocation basePath) {
        return FeywildMod.getInstance().resource("animations/tree_ent.animation.json");
    }

    @Override
    public ResourceLocation buildFormattedModelPath(ResourceLocation basePath) {
        return FeywildMod.getInstance().resource("geo/tree_ent.geo.json");
    }
}
