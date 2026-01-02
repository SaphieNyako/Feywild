package com.saphienyako.feywild.entity.model;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.WinterPixieEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WinterPixieModel extends AnimatedGeoModel<WinterPixieEntity> {

    @Override
    public ResourceLocation getModelLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "geo/winter_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "textures/entity/winter_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "animations/winter_pixie.animation.json");
    }
}
