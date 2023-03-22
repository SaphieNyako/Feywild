package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.WinterTreeEnt;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WinterTreeEntModel extends AnimatedGeoModel<WinterTreeEnt> {
    @Override
    public ResourceLocation getModelResource(WinterTreeEnt object) {
        return FeywildMod.getInstance().resource("geo/tree_ent.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WinterTreeEnt object) {
        return FeywildMod.getInstance().resource("textures/entity/winter_tree_ent.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WinterTreeEnt animatable) {
        return FeywildMod.getInstance().resource("animations/tree_ent.animation.json");
    }
}
