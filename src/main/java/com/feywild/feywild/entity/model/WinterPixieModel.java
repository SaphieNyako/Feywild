package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.WinterPixieEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WinterPixieModel extends AnimatedGeoModel<WinterPixieEntity> {

    @Override
    public ResourceLocation getModelLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "geo/winter_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "textures/entity/winter_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "animations/winter_pixie.animation.json");
    }
}
