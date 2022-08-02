package com.feywild.feywild.config.data;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.moddingx.libx.config.validate.IntRange;

import java.util.List;

public record AdvancedSpawns(
        @IntRange(min = 0) int weight,
        @IntRange(min = 1) int min,
        @IntRange(min = 1) int max,
        List<TagKey<Biome>> biomeTags
) {}
