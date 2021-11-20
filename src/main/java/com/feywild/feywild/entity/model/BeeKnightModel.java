package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.BeeKnightEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class BeeKnightModel extends AnimatedGeoModel<BeeKnightEntity> {

    @Override
    public void setLivingAnimations(BeeKnightEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

    @Override
    public ResourceLocation getModelLocation(BeeKnightEntity beeKnightEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/bee_knight.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BeeKnightEntity beeKnightEntity) {
        return beeKnightEntity.getEntityData().get(BeeKnightEntity.AGGRAVATED) ? new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/angry_bee_knight.png") : new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/bee_knight.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BeeKnightEntity beeKnightEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/bee_knight.animation.json");
    }
}
