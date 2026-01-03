package com.saphienyako.feywild.entity.model;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.WinterPixieEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WinterPixieModel extends AnimatedGeoModel<WinterPixieEntity> {
    @SuppressWarnings("removal")
    @Override
    public void setLivingAnimations(WinterPixieEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

    @Override
    public ResourceLocation getModelLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "geo/winter_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "textures/entity/winter_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "animations/winter_pixie.animation.json");
    }
}
