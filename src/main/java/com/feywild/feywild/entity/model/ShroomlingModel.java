package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Shroomling;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShroomlingModel extends AnimatedGeoModel<Shroomling> {

    @Override
    public ResourceLocation getModelResource(Shroomling shroomling) {
        return FeywildMod.getInstance().resource("geo/shroomling.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Shroomling shroomling) {
        return FeywildMod.getInstance().resource("textures/entity/shroomling.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Shroomling shroomling) {
        return FeywildMod.getInstance().resource("animations/shroomling.animation.json");
    }
}
