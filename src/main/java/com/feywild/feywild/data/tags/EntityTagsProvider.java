package com.feywild.feywild.data.tags;

import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.tag.ModEntityTags;
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
        this.tag(ModEntityTags.BOSSES).add(ModEntities.mab, ModEntities.titania);
        this.tag(ModEntityTags.TREE_ENTS).add(ModEntities.springTreeEnt, ModEntities.summerTreeEnt, ModEntities.autumnTreeEnt, ModEntities.winterTreeEnt, ModEntities.blossomTreeEnt, ModEntities.hexenTreeEnt);
        this.tag(ModEntityTags.LEVITATION_IMMUNE).addTag(ModEntityTags.BOSSES).addTag(ModEntityTags.TREE_ENTS);

        this.tag(ModEntityTags.SOUL_GEM).add(
                EntityType.SKELETON,
                EntityType.ZOMBIE,
                EntityType.ZOMBIE_VILLAGER,
                EntityType.HUSK,
                EntityType.DROWNED,
                EntityType.WITHER_SKELETON,
                EntityType.VILLAGER,
                EntityType.PILLAGER,
                EntityType.VINDICATOR,
                EntityType.EVOKER
        );
    }
}
