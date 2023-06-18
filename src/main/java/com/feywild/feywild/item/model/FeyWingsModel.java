package com.feywild.feywild.item.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.item.FeyWing;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FeyWingsModel extends GeoModel<FeyWing> {
    @Override
    public ResourceLocation getModelResource(FeyWing object) {
        return FeywildMod.getInstance().resource("geo/fey_wings.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FeyWing object) {
        return FeywildMod.getInstance().resource("textures/armor/fey_wings_" + object.variant.id + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(FeyWing animatable) {
        return FeywildMod.getInstance().resource("animations/fey_wings.animation.json");
    }
}
