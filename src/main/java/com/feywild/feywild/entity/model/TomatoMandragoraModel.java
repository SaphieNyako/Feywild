package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.mandragora.TomatoMandragoraEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TomatoMandragoraModel extends AnimatedGeoModel<TomatoMandragoraEntity> {

    @Override
    public ResourceLocation getModelLocation(TomatoMandragoraEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/mandragora.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TomatoMandragoraEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/mandragora_tomato.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TomatoMandragoraEntity animatable) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/mandragora.animation.json");
    }
}
