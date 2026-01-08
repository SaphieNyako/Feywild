package com.saphienyako.feywild.entity.model;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.AutumnPixieEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class AutumnPixieModel extends AnimatedGeoModel<AutumnPixieEntity> {

    @SuppressWarnings("removal")
    @Override
    public void setLivingAnimations(AutumnPixieEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

    @Override
    public ResourceLocation getModelLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "geo/autumn_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "textures/entity/autumn_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "animations/autumn_pixie.animation.json");
    }
}
