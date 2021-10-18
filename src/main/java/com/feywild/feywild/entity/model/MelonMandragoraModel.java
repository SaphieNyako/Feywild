package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.MelonMandragoraEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MelonMandragoraModel extends AnimatedGeoModel<MelonMandragoraEntity> {

    @Override
    public ResourceLocation getModelLocation(MelonMandragoraEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/mandragora.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MelonMandragoraEntity object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/mandragora_melon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MelonMandragoraEntity animatable) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/mandragora.animation.json");
    }
}
