package com.feywild.feywild.data.worldgen.data;

import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.NoiseData;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class FeyNoiseSettings extends NoiseData {

    public final Holder<NoiseGeneratorSettings> feywild = this.generator()
            .build(); // UPDATE_TODO adjust noise router for feywild dimension
            
    
    public FeyNoiseSettings(Properties properties) {
        super(properties);
    }
}
