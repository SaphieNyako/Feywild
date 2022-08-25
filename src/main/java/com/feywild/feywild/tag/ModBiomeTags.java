package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomeTags {

    public static final TagKey<Biome> IS_SPRING = BiomeTags.create(FeywildMod.getInstance().resource("is_spring").toString());
    public static final TagKey<Biome> IS_SUMMER = BiomeTags.create(FeywildMod.getInstance().resource("is_summer").toString());
    public static final TagKey<Biome> IS_AUTUMN = BiomeTags.create(FeywildMod.getInstance().resource("is_autumn").toString());
    public static final TagKey<Biome> IS_WINTER = BiomeTags.create(FeywildMod.getInstance().resource("is_winter").toString());
}
