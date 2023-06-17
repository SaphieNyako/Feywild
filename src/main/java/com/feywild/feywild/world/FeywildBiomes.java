package com.feywild.feywild.world;

import com.feywild.feywild.FeywildMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class FeywildBiomes {
    
    public static final ResourceKey<Biome> SPRING_BIOME = ResourceKey.create(Registry.BIOME, FeywildMod.getInstance().resource("blossoming_wealds"));
    public static final ResourceKey<Biome> SUMMER_BIOME = ResourceKey.create(Registry.BIOME, FeywildMod.getInstance().resource("golden_seelie_fields"));
    public static final ResourceKey<Biome> AUTUMN_BIOME = ResourceKey.create(Registry.BIOME, FeywildMod.getInstance().resource("eternal_fall"));
    public static final ResourceKey<Biome> WINTER_BIOME = ResourceKey.create(Registry.BIOME, FeywildMod.getInstance().resource("frozen_retreat"));
    public static final ResourceKey<Biome> FEY_OCEAN = ResourceKey.create(Registry.BIOME, FeywildMod.getInstance().resource("feywild_ocean"));
}
