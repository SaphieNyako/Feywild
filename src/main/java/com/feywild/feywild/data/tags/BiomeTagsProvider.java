package com.feywild.feywild.data.tags;

import com.feywild.feywild.data.worldgen.BiomeProvider;
import com.feywild.feywild.tag.ModBiomeTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.tags.TagProviderBase;

public class BiomeTagsProvider extends TagProviderBase<Biome> {
    
    private final BiomeProvider biomes;
    
    public BiomeTagsProvider(DatagenContext ctx) {
        super(ctx, Registries.BIOME);
        this.biomes = ctx.findRegistryProvider(BiomeProvider.class);
    }

    @Override
    protected void setup() {
        this.tag(ModBiomeTags.IS_FEYWILD_DIMENSION).add(
                biomes.blossomingWealds.value(),
                biomes.goldenSeelieFields.value(),
                biomes.eternalFall.value(),
                biomes.frozenRetreat.value(),
                biomes.feywildOcean.value()
        );
        
        this.tag(BiomeTags.IS_FOREST).add(biomes.blossomingWealds.value(), biomes.goldenSeelieFields.value(), biomes.eternalFall.value(), biomes.frozenRetreat.value());
        this.tag(BiomeTags.IS_OCEAN).add(biomes.feywildOcean.value());
        this.tag(Tags.Biomes.IS_HOT).add(biomes.goldenSeelieFields.value());
        this.tag(Tags.Biomes.IS_COLD).add(biomes.frozenRetreat.value());
        this.tag(Tags.Biomes.IS_MAGICAL).add(biomes.blossomingWealds.value(), biomes.goldenSeelieFields.value(), biomes.eternalFall.value(), biomes.frozenRetreat.value());
        
        this.tag(ModBiomeTags.IS_SPRING).add(biomes.blossomingWealds.value());
        this.tag(ModBiomeTags.IS_SUMMER).add(biomes.goldenSeelieFields.value());
        this.tag(ModBiomeTags.IS_AUTUMN).add(biomes.eternalFall.value());
        this.tag(ModBiomeTags.IS_WINTER).add(biomes.frozenRetreat.value());
    }
}
