package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.Pixie;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.resources.ResourceLocation;

public class PixieModel extends TypedEntityModel<Pixie> {
    
    public PixieModel(Alignment alignment) {
        super("pixie", alignment.id);
    }

    @Override
    public ResourceLocation buildFormattedAnimationPath(ResourceLocation basePath) {
        return FeywildMod.getInstance().resource("animations/pixie.animation.json");
    }
}
