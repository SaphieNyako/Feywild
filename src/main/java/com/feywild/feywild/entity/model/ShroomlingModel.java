package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Shroomling;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShroomlingModel extends AnimatedGeoModel<Shroomling> {

    @Override
    public ResourceLocation getModelLocation(Shroomling object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/shroomling.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Shroomling object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/shroomling.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Shroomling animatable) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/shroomling.animation.json");
    }
}
