package com.feywild.feywild.block.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.FeyAltar;
import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FeyAltarModel extends AnimatedGeoModel<FeyAltarBlockEntity> {

    @Override
    public ResourceLocation getModelLocation(FeyAltarBlockEntity feyAltarBlockEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "geo/fey_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FeyAltarBlockEntity feyAltarBlockEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "textures/block/fey_altar.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FeyAltarBlockEntity feyAltarBlockEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "animations/fey_altar.animation.json");
    }
}
