package com.feywild.feywild.data.worldgen;

import net.minecraft.core.Holder;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.sandbox.BiomeLayerProviderBase;
import org.moddingx.libx.sandbox.generator.BiomeLayer;

public class BiomeLayerProvider extends BiomeLayerProviderBase {

    private final BiomeProvider biomes = this.context.findRegistryProvider(BiomeProvider.class);
    
    public final Holder<BiomeLayer> layer = this.layer()
            .baseLayer()
            .fullRange()
            .biome(this.biomes.blossomingWealds).temperature(0, 1).humidity(0, 1).continentalness(-0.18f, 1).erosion(-1, 1).depth(-1, 1).weirdness(-1, 1).add()
            .biome(this.biomes.goldenSeelieFields).temperature(0, 1).humidity(-1, 0).continentalness(-0.18f, 1).erosion(-1, 1).depth(-1, 1).weirdness(-1, 1).add()
            .biome(this.biomes.eternalFall).temperature(-1, 0).humidity(0, 1).continentalness(-0.18f, 1).erosion(-1, 1).depth(-1, 1).weirdness(-1, 1).add()
            .biome(this.biomes.frozenRetreat).temperature(-1, 0).humidity(-1, 0).continentalness(-0.18f, 1).erosion(-1, 1).depth(-1, 1).weirdness(-1, 1).add()
            .biome(this.biomes.feywildOcean).temperature(-1, 1).humidity(-1, 1).continentalness(-1, -0.18f).erosion(-1, 1).depth(-1, 0.5f).weirdness(-1, 1).add()
            .build();

    public BiomeLayerProvider(DatagenContext ctx) {
        super(ctx);
    }
}
