package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.BotaniaPixie;
import net.minecraft.resources.ResourceLocation;

public class BotaniaPixieModel extends TypedEntityModel<BotaniaPixie> {

    public BotaniaPixieModel() {
        super("botania_pixie", null);
    }

    @Override
    public ResourceLocation getModelResource(BotaniaPixie pixie) {
        return FeywildMod.getInstance().resource("geo/botania_pixie.geo.json");
    }

    @Override
    public ResourceLocation getAnimationResource(BotaniaPixie pixie) {
        return FeywildMod.getInstance().resource("animations/botania_pixie.animation.json");
    }

    @Override
    public ResourceLocation getTextureResource(BotaniaPixie pixie) {
        return FeywildMod.getInstance().resource("textures/entity/" + pixie.getVariant().id + "/botania_pixie.png");
    }
}
