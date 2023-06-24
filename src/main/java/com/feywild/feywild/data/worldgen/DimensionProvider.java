package com.feywild.feywild.data.worldgen;

import com.feywild.feywild.tag.ModBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.LevelStem;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.Id;
import org.moddingx.libx.datagen.provider.sandbox.DimensionProviderBase;

public class DimensionProvider extends DimensionProviderBase {

    private final DimensionTypeProvider types = this.context.findRegistryProvider(DimensionTypeProvider.class);
    private final NoiseProvider noise = this.context.findRegistryProvider(NoiseProvider.class);
    private final SurfaceProvider surface = this.context.findRegistryProvider(SurfaceProvider.class);
    
    @Id("marketplace")
    public final Holder<LevelStem> marketplace = this.dimension(this.types.marketplace)
            .fixedBiome(Biomes.THE_VOID)
            .flatGenerator()
            .build();
    
    @Id("feywild")
    public final Holder<LevelStem> feywild = this.dimension(BuiltinDimensionTypes.OVERWORLD)
            .layeredBiome(ModBiomeTags.FEYWILD_LAYERS)
            .noiseGenerator(this.noise.feywild)
            .surfaceOverride(this.surface.mainSurface)
            .build();
    
    public DimensionProvider(DatagenContext ctx) {
        super(ctx);
    }
}
