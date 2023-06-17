package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Mab;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MabModel extends AnimatedGeoModel<Mab> {
    /*
    @Override
    public void setLivingAnimations(Mab entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    } */

    @Override
    public ResourceLocation getModelResource(Mab object) {
        return FeywildMod.getInstance().resource("geo/mab.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Mab object) {
        return FeywildMod.getInstance().resource("textures/entity/mab.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Mab animatable) {
        return FeywildMod.getInstance().resource("animations/mab.animation.json");
    }
}
