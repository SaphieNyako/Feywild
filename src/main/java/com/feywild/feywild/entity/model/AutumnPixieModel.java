package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.AutumnPixieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class AutumnPixieModel extends AnimatedGeoModel<AutumnPixieEntity> {

    @Override
    public ResourceLocation getModelLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "geo/autumn_pixie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "textures/entity/autumn_pixie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AutumnPixieEntity autumnPixieEntity) {
        return new ResourceLocation(FeywildMod.MOD_ID, "animations/autumn_pixie.animation.json");
    }

}
