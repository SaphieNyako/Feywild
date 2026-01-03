package com.saphienyako.feywild.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.SummerPixieEntity;
import com.saphienyako.feywild.entity.animations.SummerPixieAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
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
