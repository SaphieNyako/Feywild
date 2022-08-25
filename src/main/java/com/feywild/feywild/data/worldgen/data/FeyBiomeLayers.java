package com.feywild.feywild.data.worldgen.data;

import com.feywild.feywild.world.FeywildDimensions;
import io.github.noeppi_noeppi.mods.sandbox.biome.BiomeLayer;
import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.BiomeLayerData;
import net.minecraft.core.Holder;

public class FeyBiomeLayers extends BiomeLayerData {

    private final FeyBiomes biomes = this.resolve(FeyBiomes.class);
    
    // UPDATE_TODO adjust climate targets (together with noise router)
    public final Holder<BiomeLayer> layer = this.layer(FeywildDimensions.FEYWILD_LAYER)
            .fullRange()
            .biome(this.biomes.springBiome).temperature(0).humidity(0).continentalness(0).erosion(0).depth(0).weirdness(0).add()
            .biome(this.biomes.summerBiome).temperature(0).humidity(0).continentalness(0).erosion(0).depth(0).weirdness(0).add()
            .biome(this.biomes.autumnBiome).temperature(0).humidity(0).continentalness(0).erosion(0).depth(0).weirdness(0).add()
            .biome(this.biomes.winterBiome).temperature(0).humidity(0).continentalness(0).erosion(0).depth(0).weirdness(0).add()
            .build();

    public FeyBiomeLayers(Properties properties) {
        super(properties);
    }
}
