package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.SummerPixie;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class SummerPixieModel extends AnimatedGeoModel<SummerPixie> {


    @Override
    public void setLivingAnimations(SummerPixie entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

    @Override
    public ResourceLocation getModelLocation(SummerPixie summerPixieEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/summer_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SummerPixie summerPixieEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/summer_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SummerPixie summerPixieEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/summer_pixie.animation.json");
    }
}
