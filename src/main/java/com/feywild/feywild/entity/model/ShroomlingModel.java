package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.ShroomlingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShroomlingModel extends AnimatedGeoModel<ShroomlingEntity> {

    @Override
    public ResourceLocation getModelLocation(ShroomlingEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/shroomling.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ShroomlingEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/shroomling.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ShroomlingEntity animatable) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/shroomling.animation.json");
    }

}
