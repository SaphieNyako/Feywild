package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.SpringPixie;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class SpringPixieModel extends AnimatedGeoModel<SpringPixie> {

    @Override
    public void setLivingAnimations(SpringPixie entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

    @Override
    public ResourceLocation getModelLocation(SpringPixie springPixieEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/spring_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SpringPixie springPixieEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/spring_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SpringPixie springPixieEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/spring_pixie.animation.json");
    }
}
