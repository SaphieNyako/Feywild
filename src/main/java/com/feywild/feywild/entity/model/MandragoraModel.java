package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Mandragora;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MandragoraModel extends AnimatedGeoModel<Mandragora> {

    @Override
    public ResourceLocation getModelLocation(Mandragora object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/mandragora.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Mandragora object) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/mandragora_" + object.getVariant().name().toLowerCase() + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Mandragora animatable) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/mandragora.animation.json");
    }
}
