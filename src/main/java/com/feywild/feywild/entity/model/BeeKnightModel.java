package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.BeeKnight;
import net.minecraft.resources.ResourceLocation;
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
    public ResourceLocation getModelResource(BeeKnight beeKnight) {
        return FeywildMod.getInstance().resource("geo/bee_knight.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BeeKnight beeKnight) {
        return beeKnight.getEntityData().get(BeeKnight.AGGRAVATED) ? FeywildMod.getInstance().resource("textures/entity/angry_bee_knight.png") : FeywildMod.getInstance().resource("textures/entity/bee_knight.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BeeKnight beeKnight) {
        return FeywildMod.getInstance().resource("animations/bee_knight.animation.json");
    }
}
