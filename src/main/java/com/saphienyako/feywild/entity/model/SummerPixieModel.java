package com.saphienyako.feywild.entity.model;


import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.SummerPixieEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SummerPixieModel extends AnimatedGeoModel<SummerPixieEntity> {
    @SuppressWarnings("removal")
    @Override
    public void setLivingAnimations(SummerPixieEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
    @Override
    public ResourceLocation getModelLocation(SummerPixieEntity summerPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "geo/summer_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SummerPixieEntity summerPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "textures/entity/summer_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SummerPixieEntity summerPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "animations/summer_pixie.animation.json");
    }
}
