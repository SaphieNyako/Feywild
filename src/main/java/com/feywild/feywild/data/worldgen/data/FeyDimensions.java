package com.feywild.feywild.data.worldgen.data;

import com.feywild.feywild.tag.ModBiomeTags;
import com.feywild.feywild.world.FeywildDimensions;
import io.github.noeppi_noeppi.mods.sandbox.datagen.ext.DimensionData;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.LevelStem;

public class FeyDimensions extends DimensionData {

    private final FeyDimensionTypes types = this.resolve(FeyDimensionTypes.class);
    private final FeyNoiseSettings noise = this.resolve(FeyNoiseSettings.class);
    private final FeySurface surface = this.resolve(FeySurface.class);
    
    public final Holder<LevelStem> marketplace = this.dimension(FeywildDimensions.MARKETPLACE, this.types.marketplace)
            .fixedBiome(Biomes.THE_VOID)
            .flatGenerator()
            .build();
    
    public final Holder<LevelStem> feywild = this.dimension(FeywildDimensions.FEYWILD, this.holder(BuiltinDimensionTypes.OVERWORLD))
            .layeredBiome(48, 120, ModBiomeTags.FEYWILD_LAYERS) // UPDATE_TODO layer scale
            .noiseGenerator(this.noise.feywild)
            .surfaceOverride(this.surface.mainSurface)
            .build();
    
    public FeyDimensions(Properties properties) {
        super(properties);
    }
}
