package com.feywild.feywild.block.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.entity.ElectrifiedGroundTileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ElectrifiedGroundModel extends AnimatedGeoModel<ElectrifiedGroundTileEntity> {

    @Override
    public ResourceLocation getModelLocation(ElectrifiedGroundTileEntity feyAltarBlockEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "geo/thunder_circle.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ElectrifiedGroundTileEntity feyAltarBlockEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "textures/block/thunder_circle.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ElectrifiedGroundTileEntity feyAltarBlockEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "animations/thunder_circle.animation.json");
    }
}
