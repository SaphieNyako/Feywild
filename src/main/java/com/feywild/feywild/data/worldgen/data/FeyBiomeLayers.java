package com.feywild.feywild.data.worldgen.data;

import com.feywild.feywild.world.FeywildDimensions;
import org.moddingx.libx.sandbox.generator.BiomeLayer;
import org.moddingx.libx.datagen.provider.sandbox.BiomeLayerProviderBase;
import net.minecraft.core.Holder;

import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.base.WorldGenData.Properties;

public class FeyBiomeLayers extends BiomeLayerProviderBase {

    private final FeyBiomes biomes = this.resolve(FeyBiomes.class);
    
    public final Holder<BiomeLayer> layer = this.layer(FeywildDimensions.FEYWILD_LAYER)
            .fullRange()
            .biome(this.biomes.springBiome).temperature(0, 1).humidity(0, 1).continentalness(-0.18f, 1).erosion(-1, 1).depth(-1, 1).weirdness(-1, 1).add()
            .biome(this.biomes.summerBiome).temperature(0, 1).humidity(-1, 0).continentalness(-0.18f, 1).erosion(-1, 1).depth(-1, 1).weirdness(-1, 1).add()
            .biome(this.biomes.autumnBiome).temperature(-1, 0).humidity(0, 1).continentalness(-0.18f, 1).erosion(-1, 1).depth(-1, 1).weirdness(-1, 1).add()
            .biome(this.biomes.winterBiome).temperature(-1, 0).humidity(-1, 0).continentalness(-0.18f, 1).erosion(-1, 1).depth(-1, 1).weirdness(-1, 1).add()
            .biome(this.biomes.feywildOcean).temperature(-1, 1).humidity(-1, 1).continentalness(-1, -0.18f).erosion(-1, 1).depth(-1, 0.5f).weirdness(-1, 1).add()
            .build();

    public FeyBiomeLayers(Properties properties) {
        super(properties);
    }
}
