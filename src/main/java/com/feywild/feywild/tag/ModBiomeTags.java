package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomeTags {

    public static final TagKey<Biome> BEEKEEP = BiomeTags.create(FeywildMod.getInstance().resource("beekeep").toString());

}
