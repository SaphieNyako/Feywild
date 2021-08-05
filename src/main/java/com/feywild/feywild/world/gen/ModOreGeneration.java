package com.feywild.feywild.world.gen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ModOreGeneration {

    public static void setupOres() {
        for (OreType ore : OreType.values()) {
            // Will trigger registration
            ore.getFeature();
        }
    }

    public static void loadBiome(BiomeLoadingEvent event) {
        for (OreType ore : OreType.values()) {
            if (!event.getCategory().equals(Biome.Category.NETHER) && !event.getCategory().equals(Biome.Category.THEEND)) {
                event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore.getFeature());
            }
        }
    }
}
