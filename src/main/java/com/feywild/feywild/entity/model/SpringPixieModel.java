package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.SpringPixieEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpringPixieModel extends AnimatedGeoModel<SpringPixieEntity> {

    @Override
    public ResourceLocation getModelLocation(SpringPixieEntity springPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "geo/spring_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SpringPixieEntity springPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "textures/entity/spring_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SpringPixieEntity springPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "animations/spring_pixie.animation.json");
    }
}
