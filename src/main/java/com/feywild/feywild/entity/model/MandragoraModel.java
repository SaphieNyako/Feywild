package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Mandragora;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.Locale;

public class MandragoraModel extends AnimatedGeoModel<Mandragora> {

    @Override
    public ResourceLocation getModelResource(Mandragora mandragora) {
        return FeywildMod.getInstance().resource("geo/mandragora.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Mandragora mandragora) {
        return FeywildMod.getInstance().resource("textures/entity/mandragora_" + mandragora.getVariant().name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(Mandragora mandragora) {
        return FeywildMod.getInstance().resource("animations/mandragora.animation.json");
    }
}
