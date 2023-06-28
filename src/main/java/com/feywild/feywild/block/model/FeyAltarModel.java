package com.feywild.feywild.block.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.FeyAltarBlock;
import com.feywild.feywild.block.entity.FeyAltar;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FeyAltarModel extends GeoModel<FeyAltar> {

    @Override
    public ResourceLocation getModelResource(FeyAltar feyAltar) {
        FeyAltarBlock feyAltarBlock = (FeyAltarBlock) feyAltar.getBlockState().getBlock();
        return FeywildMod.getInstance().resource("geo/" + feyAltarBlock.getAlignment().id + "/fey_altar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FeyAltar feyAltar) {
        FeyAltarBlock feyAltarBlock = (FeyAltarBlock) feyAltar.getBlockState().getBlock();
        return FeywildMod.getInstance().resource("textures/block/" + feyAltarBlock.getAlignment().id + "_fey_altar.png");

    }

    @Override
    public ResourceLocation getAnimationResource(FeyAltar feyAltar) {
        FeyAltarBlock feyAltarBlock = (FeyAltarBlock) feyAltar.getBlockState().getBlock();
        return FeywildMod.getInstance().resource("animations/" + feyAltarBlock.getAlignment().id + "/fey_altar.animation.json");
    }
}
