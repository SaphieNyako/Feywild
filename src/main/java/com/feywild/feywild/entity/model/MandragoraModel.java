package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Mandragora;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class MandragoraModel extends TypedEntityModel<Mandragora> {

    public MandragoraModel(@Nonnull String subType) {
        super("mandragora", subType);
    }

    @Override
    public ResourceLocation getModelResource(Mandragora mandragora) {
        return FeywildMod.getInstance().resource("geo/mandragora.geo.json");
    }

    @Override
    public ResourceLocation getAnimationResource(Mandragora mandragora) {
        return FeywildMod.getInstance().resource("animations/mandragora.animation.json");
    }
}
