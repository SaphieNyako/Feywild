package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.Objects;

public class DwarfBlacksmithModel extends AnimatedGeoModel<DwarfBlacksmithEntity> {

    @Override
    public void setLivingAnimations(DwarfBlacksmithEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(MathHelper.clamp(extraData.netHeadYaw * ((float) Math.PI / 180F), -0.8f, 0.8f));
        }
    }

    @Override
    public ResourceLocation getModelLocation(DwarfBlacksmithEntity dwarfBlacksmithEntity) {
        String string = Objects.requireNonNull(dwarfBlacksmithEntity.getType().getRegistryName()).getPath();

        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/"+string+".geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DwarfBlacksmithEntity dwarfBlacksmithEntity) {
        String string = Objects.requireNonNull(dwarfBlacksmithEntity.getType().getRegistryName()).getPath();
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/"+string+".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DwarfBlacksmithEntity dwarfBlacksmithEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/dwarf_blacksmith.animation.json");
    }

}
