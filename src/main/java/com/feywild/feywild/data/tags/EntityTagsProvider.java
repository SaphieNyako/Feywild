package com.feywild.feywild.data.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.tags.TagProviderBase;

public class EntityTagsProvider extends TagProviderBase<EntityType<?>> {
    
    public EntityTagsProvider(DatagenContext ctx) {
        super(ctx, Registries.ENTITY_TYPE);
    }

    @Override
    protected void setup() {
        // UPDATE_TODO boss tags
    }
}
