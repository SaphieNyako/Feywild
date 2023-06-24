package com.feywild.feywild.data.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.world.level.dimension.DimensionType;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.sandbox.DimensionTypeProviderBase;

public class DimensionTypeProvider extends DimensionTypeProviderBase {

    public final Holder<DimensionType> marketplace = this.dimension()
            .disableRaids()
            .respawnDevices(false, false)
            .height(0, 256)
            .build();
    
    public DimensionTypeProvider(DatagenContext ctx) {
        super(ctx);
    }
}
