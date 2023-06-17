package com.feywild.feywild.data.worldgen.data;

import org.moddingx.libx.datagen.provider.sandbox.DimensionTypeProviderBase;
import net.minecraft.core.Holder;
import net.minecraft.world.level.dimension.DimensionType;

import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.base.WorldGenData.Properties;

public class FeyDimensionTypes extends DimensionTypeProviderBase {

    public final Holder<DimensionType> marketplace = this.dimension()
            .disableRaids()
            .respawnDevices(false, false)
            .height(0, 256)
            .build();
    
    public FeyDimensionTypes(Properties properties) {
        super(properties);
    }
}
