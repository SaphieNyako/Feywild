package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.SummerPixieEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class SummerPixieModel extends AnimatedGeoModel<SummerPixieEntity> {


    @Override
    public void setLivingAnimations(SummerPixieEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }


    @Override
    public ResourceLocation getModelLocation(SummerPixieEntity summerPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "geo/summer_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SummerPixieEntity summerPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "textures/entity/summer_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SummerPixieEntity summerPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "animations/summer_pixie.animation.json");
    }
}
