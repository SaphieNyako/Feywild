package com.saphienyako.feywild.entity.model;


import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.SpringPixieEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpringPixieModel extends AnimatedGeoModel<SpringPixieEntity> {

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