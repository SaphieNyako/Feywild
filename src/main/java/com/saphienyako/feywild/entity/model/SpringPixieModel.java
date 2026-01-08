package com.saphienyako.feywild.entity.model;


import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.SpringPixieEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SpringPixieModel extends AnimatedGeoModel<SpringPixieEntity> {
	@SuppressWarnings("removal")
	@Override
	public void setLivingAnimations(SpringPixieEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");
		if (customPredicate != null) {
			//noinspection unchecked
			EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}

	@Override
	public ResourceLocation getModelLocation(SpringPixieEntity springPixieEntity) {
		return new ResourceLocation(Feywild.MOD_ID, "geo/spring_pixie.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SpringPixieEntity springPixieEntity) {
		return new ResourceLocation(Feywild.MOD_ID, "textures/entity/spring_pixie.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SpringPixieEntity springPixieEntity) {
		return new ResourceLocation(Feywild.MOD_ID, "animations/spring_pixie.animation.json");
	}
}