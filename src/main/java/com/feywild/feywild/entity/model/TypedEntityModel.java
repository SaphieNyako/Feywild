package com.feywild.feywild.entity.model;

import com.feywild.feywild.FeywildMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

import javax.annotation.Nullable;

public class TypedEntityModel<T extends GeoAnimatable> extends DefaultedEntityGeoModel<T> {
    
    @Nullable 
    private final String subType;

    public TypedEntityModel(String type, @Nullable String subType) {
        super(FeywildMod.getInstance().resource(type), true);
        this.subType = subType;
        ResourceLocation id = FeywildMod.getInstance().resource(type);
        this.withAltModel(id);
		this.withAltTexture(id);
		this.withAltAnimations(id);
    }

    @Override
    protected String subtype() {
        return this.subType == null ? "entity" : this.subType;
    }

    @Override
    public ResourceLocation buildFormattedModelPath(ResourceLocation basePath) {
        return new ResourceLocation(basePath.getNamespace(), "geo/" + (subType == null ? "" : subType + "/") + basePath.getPath() + ".geo.json");
    }

    @Override
    public ResourceLocation buildFormattedAnimationPath(ResourceLocation basePath) {
        return new ResourceLocation(basePath.getNamespace(), "animations/" + (subType == null ? "" : subType + "/") + basePath.getPath() + ".animation.json");
    }

    @Override
    public ResourceLocation buildFormattedTexturePath(ResourceLocation basePath) {
        return new ResourceLocation(basePath.getNamespace(), "textures/entity/" + (subType == null ? "" : subType + "/") + basePath.getPath() + ".png");
    }
}
