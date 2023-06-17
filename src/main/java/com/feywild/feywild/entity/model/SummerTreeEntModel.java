package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.SummerTreeEnt;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SummerTreeEntModel extends AnimatedGeoModel<SummerTreeEnt> {
    @Override
    public ResourceLocation getModelResource(SummerTreeEnt object) {
        return FeywildMod.getInstance().resource("geo/tree_ent.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SummerTreeEnt object) {
        return FeywildMod.getInstance().resource("textures/entity/summer_tree_ent.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SummerTreeEnt animatable) {
        return FeywildMod.getInstance().resource("animations/tree_ent.animation.json");
    }
}
