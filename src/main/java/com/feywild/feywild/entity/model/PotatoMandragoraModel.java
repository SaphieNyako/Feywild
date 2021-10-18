package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.PotatoMandragoraEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PotatoMandragoraModel extends AnimatedGeoModel<PotatoMandragoraEntity> {

    @Override
    public ResourceLocation getModelLocation(PotatoMandragoraEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/mandragora.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PotatoMandragoraEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/mandragora_potato.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PotatoMandragoraEntity animatable) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/mandragora.animation.json");
    }
}
