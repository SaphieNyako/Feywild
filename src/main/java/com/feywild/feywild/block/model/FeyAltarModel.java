package com.feywild.feywild.block.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.entity.FeyAltar;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FeyAltarModel extends AnimatedGeoModel<FeyAltar> {
    
    @Override
    public ResourceLocation getModelResource(FeyAltar feyAltar) {
        return FeywildMod.getInstance().resource("geo/fey_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FeyAltar feyAltar) {
        return FeywildMod.getInstance().resource("textures/block/fey_altar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FeyAltar animatable) {
        return FeywildMod.getInstance().resource("animations/fey_altar.animation.json");
    }
}
