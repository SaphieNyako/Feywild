package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Titania;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class TitaniaModel extends AnimatedGeoModel<Titania> {

    @Override
    public void setLivingAnimations(Titania entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

    @Override
    public ResourceLocation getModelResource(Titania object) {
        return FeywildMod.getInstance().resource("geo/titania.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Titania object) {
        return FeywildMod.getInstance().resource("textures/entity/titania.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Titania animatable) {
        return FeywildMod.getInstance().resource("animations/titania.animation.json");
    }
}
