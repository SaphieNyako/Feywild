package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.PumpkinMandragoraEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PumpkinMandragoraModel extends AnimatedGeoModel<PumpkinMandragoraEntity> {

    @Override
    public ResourceLocation getModelLocation(PumpkinMandragoraEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/mandragora.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PumpkinMandragoraEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/mandragora_pumpkin.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PumpkinMandragoraEntity animatable) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/mandragora.animation.json");
    }
}
