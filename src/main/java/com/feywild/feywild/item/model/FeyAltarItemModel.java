package com.feywild.feywild.item.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.item.FeyAltarItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FeyAltarItemModel extends AnimatedGeoModel<FeyAltarItem> {

    @Override
    public ResourceLocation getModelResource(FeyAltarItem feyAltar) {
        return FeywildMod.getInstance().resource("geo/fey_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FeyAltarItem feyAltar) {
        return FeywildMod.getInstance().resource("textures/block/fey_altar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FeyAltarItem animatable) {
        return FeywildMod.getInstance().resource("animations/fey_altar.animation.json");
    }
}
