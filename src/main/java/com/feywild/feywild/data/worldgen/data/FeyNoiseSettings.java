package com.feywild.feywild.data.worldgen.data;

import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.NoiseData;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.*;

public class FeyNoiseSettings extends NoiseData {

    public final Holder<DensityFunction> feywildRidges = this.density(
            DensityFunctions.add(
                    new DensityFunctions.HolderHolder(this.holder(NoiseRouterData.RIDGES)),
                    DensityFunctions.constant(0.1)
            )
    );

    public final Holder<NoiseGeneratorSettings> feywild = this.generator()
            .router().ridges(this.feywildRidges)
            .build();
            
    
    public FeyNoiseSettings(Properties properties) {
        super(properties);
    }
}
