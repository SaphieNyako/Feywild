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
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SummerPixieModel extends AnimatedGeoModel<SummerPixieEntity> {
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
