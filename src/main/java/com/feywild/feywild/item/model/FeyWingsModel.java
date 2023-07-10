package com.feywild.feywild.item.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.item.FeyWing;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FeyWingsModel extends GeoModel<FeyWing> {
    
    private final FeyWing.Variant variant;

    public FeyWingsModel(FeyWing.Variant variant) {
        this.variant = variant;
    }

    @Override
    public ResourceLocation getModelResource(FeyWing wing) {
        return FeywildMod.getInstance().resource("geo/fey_wings.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FeyWing wing) {
        return FeywildMod.getInstance().resource("textures/armor/fey_wings_" + this.variant.id + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(FeyWing wing) {
        return FeywildMod.getInstance().resource("animations/fey_wings.animation.json");
    }
}
