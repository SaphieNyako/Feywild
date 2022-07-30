package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.DwarfBlacksmith;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.registries.ForgeRegistries;
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
    public ResourceLocation getModelResource(DwarfBlacksmith dwarfBlacksmithEntity) {
        String typeStr = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(dwarfBlacksmithEntity.getType())).getPath();
        return FeywildMod.getInstance().resource("geo/" + typeStr + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DwarfBlacksmith dwarfBlacksmithEntity) {
        String typeStr = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(dwarfBlacksmithEntity.getType())).getPath();
        return FeywildMod.getInstance().resource("textures/entity/" + typeStr + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(DwarfBlacksmith dwarfBlacksmithEntity) {
        return FeywildMod.getInstance().resource("animations/dwarf_blacksmith.animation.json");
    }
}
