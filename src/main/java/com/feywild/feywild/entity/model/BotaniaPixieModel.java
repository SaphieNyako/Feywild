package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.BotaniaPixie;
import net.minecraft.resources.ResourceLocation;

public class BotaniaPixieModel extends TypedEntityModel<BotaniaPixie> {

    public BotaniaPixieModel(BotaniaPixie.BotaniaPixieVariant variant) {
        super("botania_pixie", variant.id);
    }

    @Override
    public ResourceLocation getModelResource(BotaniaPixie pixie) {
        return FeywildMod.getInstance().resource("geo/botania_pixie.geo.json");
    }

    @Override
    public ResourceLocation getAnimationResource(BotaniaPixie pixie) {
        return FeywildMod.getInstance().resource("animations/botania_pixie.animation.json");
    }
}
