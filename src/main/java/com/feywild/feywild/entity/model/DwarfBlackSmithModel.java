package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DwarfBlackSmithModel extends AnimatedGeoModel<DwarfBlacksmithEntity> {

    @Override
    public ResourceLocation getModelLocation(DwarfBlacksmithEntity dwarfBlacksmithEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "geo/dwarf_blacksmith.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DwarfBlacksmithEntity dwarfBlacksmithEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "textures/entity/dwarf_blacksmith.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DwarfBlacksmithEntity dwarfBlacksmithEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "animations/dwarf_blacksmith.animation.json");
    }
}
