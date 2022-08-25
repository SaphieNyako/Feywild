package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import io.github.noeppi_noeppi.mods.sandbox.SandBox;
import io.github.noeppi_noeppi.mods.sandbox.biome.BiomeLayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomeTags {

    public static final TagKey<BiomeLayer> FEYWILD_LAYERS = TagKey.create(SandBox.BIOME_LAYER_REGISTRY, FeywildMod.getInstance().resource("feywild_layers"));
    
    public static final TagKey<Biome> IS_FEYWILD_DIMENSION = BiomeTags.create(FeywildMod.getInstance().resource("is_feywild_dimension").toString());
    public static final TagKey<Biome> IS_SPRING = BiomeTags.create(FeywildMod.getInstance().resource("is_spring").toString());
    public static final TagKey<Biome> IS_SUMMER = BiomeTags.create(FeywildMod.getInstance().resource("is_summer").toString());
    public static final TagKey<Biome> IS_AUTUMN = BiomeTags.create(FeywildMod.getInstance().resource("is_autumn").toString());
    public static final TagKey<Biome> IS_WINTER = BiomeTags.create(FeywildMod.getInstance().resource("is_winter").toString());
}
