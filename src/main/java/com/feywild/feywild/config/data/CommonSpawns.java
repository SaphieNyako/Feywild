package com.feywild.feywild.config.data;

import org.moddingx.libx.config.validate.IntRange;

public record CommonSpawns(
        @IntRange(min = 0) int weight,
        @IntRange(min = 1) int min,
        @IntRange(min = 1) int max
) {}
