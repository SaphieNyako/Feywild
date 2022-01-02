package com.feywild.feywild.config.data;

import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

public record AdvancedSpawns(int weight, int min, int max, List<BiomeDictionary.Type> biomes) {

}
