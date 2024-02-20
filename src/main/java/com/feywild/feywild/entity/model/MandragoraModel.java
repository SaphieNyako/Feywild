package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Mandragora;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class MandragoraModel extends TypedEntityModel<Mandragora> {

    public MandragoraModel() {
        super("mandragora", null);
    }

    @Override
    public ResourceLocation getModelResource(Mandragora mandragora) {
        return FeywildMod.getInstance().resource("geo/mandragora.geo.json");
    }

    @Override
    public ResourceLocation getAnimationResource(Mandragora mandragora) {
        return FeywildMod.getInstance().resource("animations/mandragora.animation.json");
    }

    @Override
    public ResourceLocation getTextureResource(Mandragora mandragora) {
        return FeywildMod.getInstance().resource("textures/entity/mandragora/" + mandragora.getVariant().name().toLowerCase(Locale.ROOT) + ".png");
    }
}
