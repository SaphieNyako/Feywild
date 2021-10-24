package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.DwarfBlacksmith;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.Objects;

public class DwarfBlacksmithModel extends AnimatedGeoModel<DwarfBlacksmith> {

    @Override
    public void setLivingAnimations(DwarfBlacksmith entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (customPredicate != null) {
            //noinspection unchecked
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationY(Mth.clamp(extraData.netHeadYaw * ((float) Math.PI / 180F), -0.8f, 0.8f));
        }
    }

    @Override
    public ResourceLocation getModelLocation(DwarfBlacksmith dwarfBlacksmithEntity) {
        String string = Objects.requireNonNull(dwarfBlacksmithEntity.getType().getRegistryName()).getPath();
        return new ResourceLocation(FeywildMod.getInstance().modid, "geo/" + string + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DwarfBlacksmith dwarfBlacksmithEntity) {
        String string = Objects.requireNonNull(dwarfBlacksmithEntity.getType().getRegistryName()).getPath();
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/entity/" + string + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DwarfBlacksmith dwarfBlacksmithEntity) {
        return new ResourceLocation(FeywildMod.getInstance().modid, "animations/dwarf_blacksmith.animation.json");
    }

}
