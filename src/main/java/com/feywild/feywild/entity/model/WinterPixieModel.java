package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.WinterPixie;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class WinterPixieModel extends AnimatedGeoModel<WinterPixie> {


    @Override
    public void setLivingAnimations(WinterPixie pixie, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(pixie, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }


    @Override
    public ResourceLocation getModelResource(WinterPixie pixie) {
        return FeywildMod.getInstance().resource("geo/winter_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WinterPixie pixie) {
        return FeywildMod.getInstance().resource("textures/entity/winter_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WinterPixie pixie) {
        return FeywildMod.getInstance().resource("animations/winter_pixie.animation.json");
    }
}
