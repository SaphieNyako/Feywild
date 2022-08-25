package com.feywild.feywild.data.worldgen.data;

import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.DimensionTypeData;
import net.minecraft.core.Holder;
import net.minecraft.world.level.dimension.DimensionType;

public class FeyDimensionTypes extends DimensionTypeData {

    public final Holder<DimensionType> marketplace = this.dimension()
            .disableRaids()
            .respawnDevices(false, false)
            .height(0, 256)
            .build();
    
    public FeyDimensionTypes(Properties properties) {
        super(properties);
    }
}
