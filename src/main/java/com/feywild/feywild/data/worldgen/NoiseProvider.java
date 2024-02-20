package com.feywild.feywild.data.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.*;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.sandbox.NoiseProviderBase;

public class NoiseProvider extends NoiseProviderBase {

    public final Holder<DensityFunction> feywildTemperature = this.density(DensityFunctions.noise(this.holder(Noises.TEMPERATURE), 0.25, 1));
    public final Holder<DensityFunction> feywildVegetation = this.density(DensityFunctions.noise(this.holder(Noises.VEGETATION), 0.25, 1));

    public final Holder<DensityFunction> feywildContinents = this.density(
            DensityFunctions.add(
                    new DensityFunctions.HolderHolder(this.holder(NoiseRouterData.CONTINENTS)),
                    DensityFunctions.constant(0.05)
            )
    );

    public final Holder<NoiseGeneratorSettings> feywild = this.generator()
            .router().temperature(this.feywildTemperature)
            .router().vegetation(this.feywildVegetation)
            .router().continents(this.feywildContinents)
            .build();

    public NoiseProvider(DatagenContext ctx) {
        super(ctx);
    }
}
