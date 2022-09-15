package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.base.BotaniaPixie;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BotaniaPixieModel extends AnimatedGeoModel<BotaniaPixie> {
    @Override
    public ResourceLocation getModelResource(BotaniaPixie pixie) {
        return FeywildMod.getInstance().resource("geo/botania_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BotaniaPixie pixie) {
        return FeywildMod.getInstance().resource("textures/entity/" + pixie.getAlignment().id + "_botania_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BotaniaPixie pixie) {
        return FeywildMod.getInstance().resource("animations/botania_pixie.animation.json");
    }
}
