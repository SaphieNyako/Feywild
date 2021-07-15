package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.AutumnPixieEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class AutumnPixieModel extends AnimatedGeoModel<AutumnPixieEntity> {

    @Override
    public void setLivingAnimations(AutumnPixieEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }

    @Override
    public ResourceLocation getModelLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "geo/autumn_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "textures/entity/autumn_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "animations/autumn_pixie.animation.json");
    }

}
