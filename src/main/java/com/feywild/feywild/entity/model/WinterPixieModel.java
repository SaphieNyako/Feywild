package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.entity.WinterPixieEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class WinterPixieModel extends AnimatedGeoModel<WinterPixieEntity> {


    @Override
    public void setLivingAnimations(WinterPixieEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }


    @Override
    public ResourceLocation getModelLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "geo/winter_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "textures/entity/winter_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "animations/winter_pixie.animation.json");
    }
}
