package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.AutumnTreeEnt;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AutumnTreeEntModel extends AnimatedGeoModel<AutumnTreeEnt> {
    @Override
    public ResourceLocation getModelResource(AutumnTreeEnt object) {
        return FeywildMod.getInstance().resource("geo/tree_ent.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AutumnTreeEnt object) {
        return FeywildMod.getInstance().resource("textures/entity/autumn_tree_ent.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AutumnTreeEnt animatable) {
        return FeywildMod.getInstance().resource("animations/tree_ent.animation.json");
    }
}
