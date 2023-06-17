package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.SpringTreeEnt;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpringTreeEntModel extends AnimatedGeoModel<SpringTreeEnt> {
    @Override
    public ResourceLocation getModelResource(SpringTreeEnt object) {
        return FeywildMod.getInstance().resource("geo/tree_ent.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SpringTreeEnt object) {
        return FeywildMod.getInstance().resource("textures/entity/spring_tree_ent.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SpringTreeEnt animatable) {
        return FeywildMod.getInstance().resource("animations/tree_ent.animation.json");
    }
}
