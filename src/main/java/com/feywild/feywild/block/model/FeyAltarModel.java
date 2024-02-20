package com.feywild.feywild.block.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.FeyAltarBlock;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FeyAltarModel<T extends FeyAltarBlock.FeyAltarModelProperties> extends GeoModel<T> {

    @Override
    public ResourceLocation getModelResource(T feyAltar) {
        return FeywildMod.getInstance().resource("geo/" + feyAltar.getAlignment().id + "/fey_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T feyAltar) {
        return FeywildMod.getInstance().resource("textures/block/" + feyAltar.getAlignment().id + "_fey_altar.png");

    }

    @Override
    public ResourceLocation getAnimationResource(T feyAltar) {
        return FeywildMod.getInstance().resource("animations/" + feyAltar.getAlignment().id + "/fey_altar.animation.json");
    }
}
