package com.feywild.feywild.world;

import com.feywild.feywild.world.gen.ModOreGeneration;
import com.feywild.feywild.world.gen.ModTreeGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class BiomeLoader {
    
    public static void loadBiome(BiomeLoadingEvent event) {
        ModOreGeneration.loadBiome(event);
        ModTreeGeneration.loadBiome(event);
    }
}
