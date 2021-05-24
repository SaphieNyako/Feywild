package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.SummerPixieEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SummerPixieModel extends AnimatedGeoModel<SummerPixieEntity> {

    @Override
    public ResourceLocation getModelLocation(SummerPixieEntity summerPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "geo/summer_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SummerPixieEntity summerPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "textures/entity/summer_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SummerPixieEntity summerPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "animations/summer_pixie.animation.json");
    }
}
