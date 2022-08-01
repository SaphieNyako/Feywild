package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomeTags {

    public static final TagKey<Biome> BEEKEEP = BiomeTags.create(FeywildMod.getInstance().resource("beekeep_biomes").toString());
    public static final TagKey<Biome> SPRING_WORLD_TREE = BiomeTags.create(FeywildMod.getInstance().resource("spring_world_tree_biomes").toString());
    public static final TagKey<Biome> SUMMER_WORLD_TREE = BiomeTags.create(FeywildMod.getInstance().resource("summer_world_tree_biomes").toString());
    public static final TagKey<Biome> AUTUMN_WORLD_TREE = BiomeTags.create(FeywildMod.getInstance().resource("autumn_world_tree_biomes").toString());
    public static final TagKey<Biome> WINTER_WORLD_TREE = BiomeTags.create(FeywildMod.getInstance().resource("winter_world_tree_biomes").toString());
    public static final TagKey<Biome> FEY_CIRCLE = BiomeTags.create(FeywildMod.getInstance().resource("fey_circle_biomes").toString());
}
