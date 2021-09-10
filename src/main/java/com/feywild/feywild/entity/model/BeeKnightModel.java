package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.BeeKnight;
import com.feywild.feywild.entity.SpringPixieEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class BeeKnightModel extends AnimatedGeoModel<BeeKnight> {

    @Override
    public void setLivingAnimations(BeeKnight entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
    
    @Override
    public ResourceLocation getModelLocation(BeeKnight springPixieEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/bee_knight.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BeeKnight springPixieEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/bee_knight.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BeeKnight springPixieEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/bee_knight.animation.json");
    }
}
