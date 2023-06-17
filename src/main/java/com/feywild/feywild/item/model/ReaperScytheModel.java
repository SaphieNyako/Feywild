package com.feywild.feywild.item.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.item.ReaperScythe;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ReaperScytheModel extends AnimatedGeoModel<ReaperScythe> {
    @Override
    public ResourceLocation getModelResource(ReaperScythe object) {
        return FeywildMod.getInstance().resource("geo/reaper_scythe.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ReaperScythe object) {
        return FeywildMod.getInstance().resource("textures/item/reaper_scythe.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ReaperScythe animatable) {
        return FeywildMod.getInstance().resource("animations/reaper_scythe.animation.json");
    }
}
