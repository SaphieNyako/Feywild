package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.BlossomTreeEnt;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlossomTreeEntModel extends AnimatedGeoModel<BlossomTreeEnt> {
    @Override
    public ResourceLocation getModelResource(BlossomTreeEnt object) {
        return FeywildMod.getInstance().resource("geo/tree_ent.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlossomTreeEnt object) {
        return FeywildMod.getInstance().resource("textures/entity/blossom_tree_ent.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlossomTreeEnt animatable) {
        return FeywildMod.getInstance().resource("animations/tree_ent.animation.json");
    }
}
