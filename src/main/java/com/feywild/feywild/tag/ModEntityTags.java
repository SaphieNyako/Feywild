package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import org.moddingx.libx.sandbox.SandBox;
import org.moddingx.libx.sandbox.generator.BiomeLayer;

public class ModEntityTags {

    // UPDATE_TODO add tree bosses
    public static final TagKey<EntityType<?>> BOSSES = EntityTypeTags.create(FeywildMod.getInstance().resource("bosses").toString());
    
    // UPDATE_TODO add tree ents and bosses
    public static final TagKey<EntityType<?>> LEVITATION_IMMUNE = EntityTypeTags.create(FeywildMod.getInstance().resource("levitation_immune").toString());
    
    // UPDATE_TODO add skeleton, zombie, zombie villager, husk, wither skeleton, villager, pillager, vindicator, evoker
    public static final TagKey<EntityType<?>> SOUL_GEM = EntityTypeTags.create(FeywildMod.getInstance().resource("soul_gem").toString());
}
