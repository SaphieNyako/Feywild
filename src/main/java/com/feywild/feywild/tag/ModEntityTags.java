package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTags {

    public static final TagKey<EntityType<?>> BOSSES = EntityTypeTags.create(FeywildMod.getInstance().resource("bosses").toString());
    public static final TagKey<EntityType<?>> TREE_ENTS = EntityTypeTags.create(FeywildMod.getInstance().resource("tree_ents").toString());
    public static final TagKey<EntityType<?>> LEVITATION_IMMUNE = EntityTypeTags.create(FeywildMod.getInstance().resource("levitation_immune").toString());
    public static final TagKey<EntityType<?>> SOUL_GEM = EntityTypeTags.create(FeywildMod.getInstance().resource("soul_gem").toString());
}
