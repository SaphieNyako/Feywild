package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.HexenTreeEnt;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HexenTreeEntModel extends AnimatedGeoModel<HexenTreeEnt> {
    @Override
    public ResourceLocation getModelResource(HexenTreeEnt object) {
        return FeywildMod.getInstance().resource("geo/tree_ent.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HexenTreeEnt object) {
        return FeywildMod.getInstance().resource("textures/entity/hexen_tree_ent.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HexenTreeEnt animatable) {
        return FeywildMod.getInstance().resource("animations/tree_ent.animation.json");
    }
}
