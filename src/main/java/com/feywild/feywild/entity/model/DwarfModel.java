package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.DwarfBlacksmith;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class DwarfModel extends TypedEntityModel<DwarfBlacksmith> {

    public DwarfModel(@Nonnull String subType) {
        super("dwarf", subType);
    }

    @Override
    public ResourceLocation buildFormattedAnimationPath(ResourceLocation basePath) {
        return FeywildMod.getInstance().resource("animations/dwarf_blacksmith.animation.json");
    }
}
