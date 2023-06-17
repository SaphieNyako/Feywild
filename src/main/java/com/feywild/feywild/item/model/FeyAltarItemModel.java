package com.feywild.feywild.item.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.item.FeyAltarItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FeyAltarItemModel extends AnimatedGeoModel<FeyAltarItem> {

    @Override
    public ResourceLocation getModelResource(FeyAltarItem feyAltar) {
        return FeywildMod.getInstance().resource("geo/" + feyAltar.getAlignment().id + "_fey_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FeyAltarItem feyAltar) {
        return FeywildMod.getInstance().resource("textures/block/" + feyAltar.getAlignment().id + "_fey_altar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FeyAltarItem feyAltarItem) {
        return FeywildMod.getInstance().resource("animations/" + feyAltarItem.getAlignment().id + "_fey_altar.animation.json");
    }
}
