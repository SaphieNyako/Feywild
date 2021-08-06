package com.feywild.feywild.block.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.entity.FeyAltar;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FeyAltarModel extends AnimatedGeoModel<FeyAltar> {

    @Override
    public ResourceLocation getModelLocation(FeyAltar feyAltar) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/fey_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FeyAltar feyAltar) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/block/fey_altar.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FeyAltar feyAltar) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/fey_altar.animation.json");
    }
}
