package com.feywild.feywild.config.data;

import org.moddingx.libx.config.validate.IntRange;

public record OreData(
        int weight,
        int size,
        @IntRange(min = -64, max = 320) int min_height,
        @IntRange(min = -64, max = 320) int max_height
) {}
