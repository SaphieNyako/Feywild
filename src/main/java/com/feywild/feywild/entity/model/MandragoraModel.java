package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.MandragoraEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.Objects;

public class MandragoraModel extends AnimatedGeoModel<MandragoraEntity> {

    @Override
    public ResourceLocation getModelLocation(MandragoraEntity mandragoraEntity) {
        String string = Objects.requireNonNull(mandragoraEntity.getType().getRegistryName()).getPath();

        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/" + string + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MandragoraEntity mandragoraEntity) {
        String string = Objects.requireNonNull(mandragoraEntity.getType().getRegistryName()).getPath();

        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/" + string + "_" + mandragoraEntity.getVariation().toString().toLowerCase() + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MandragoraEntity mandragoraEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/mandragora.animation.json");
    }
}
