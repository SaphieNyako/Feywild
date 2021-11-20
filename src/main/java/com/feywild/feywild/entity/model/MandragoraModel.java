package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.MandragoraEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MandragoraModel extends AnimatedGeoModel<MandragoraEntity> {

    @Override
    public ResourceLocation getModelLocation(MandragoraEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/mandragora.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MandragoraEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/mandragora_" + object.getVariation().name().toLowerCase() + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MandragoraEntity animatable) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/mandragora.animation.json");
    }
}
