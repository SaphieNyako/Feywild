package com.saphienyako.feywild.entity.model;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.AutumnPixieEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AutumnPixieModel extends AnimatedGeoModel<AutumnPixieEntity> {

    @Override
    public ResourceLocation getModelLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "geo/autumn_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "textures/entity/autumn_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "animations/autumn_pixie.animation.json");
    }
}
